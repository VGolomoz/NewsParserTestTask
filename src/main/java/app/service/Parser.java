package app.service;

import app.exceptions.CustomException;
import app.service.interfaces.NewsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static app.config.MyConstants.*;
import static app.exceptions.Errors.*;

public class Parser {

    private NewsService newsService;
    private WordsBuffer wordsBuffer;
    public static AtomicInteger writeThreadsFinished = new AtomicInteger(0);


    public Parser(NewsService newsService, WordsBuffer wordsBuffer) {
        this.newsService = newsService;
        this.wordsBuffer = wordsBuffer;
    }

    public void start() {

        List<String> categories = new ArrayList<>();
        categories.add(POLITICS_CATEGORY);
        categories.add(WAR_CATEGORY);
        categories.add(SOCIETY_CATEGORY);
        categories.add(WORLD_CATEGORY);
        categories.add(HEALTH_CATEGORY);

        categories.forEach(c -> new Thread(() -> {

            try {
                parseAndSave(c);
            } catch (CustomException e) {
                e.getMessage();
            }
        }).start());
    }

    private void parseAndSave(String category) throws CustomException {

        boolean writingIsActive = true;

        try {
            Document categoryPage = Jsoup
                    .connect(category)
                    .userAgent(USER_AGENT)
                    .validateTLSCertificates(false)
                    .get();

            if (categoryPage == null) {
                throw new CustomException(CATEGORY_PAGE_ERROR);
            }

            String linkToLastNewsFromCategory = categoryPage
                    .select(CSS_QUERY_WITH_LAST_PUBLICATIONS)
                    .select("a[href]")
                    .get(0).attr("href");

            if (linkToLastNewsFromCategory == null) {
                throw new CustomException(LINK_TO_LAST_NEWS_ERROR);
            }

            Document lastNewsPageFromCategory = Jsoup.connect(linkToLastNewsFromCategory)
                    .userAgent(USER_AGENT)
                    .validateTLSCertificates(false)
                    .get();

            if (lastNewsPageFromCategory == null) {
                throw new CustomException(LAST_NEWS_PAGE_ERROR);
            }

            String newsTitle = lastNewsPageFromCategory.title();
            String newsPostDate = lastNewsPageFromCategory
                    .select(CSS_QUERY_WITH_NEWS_POST_DATE)
                    .first()
                    .text();

            newsService.createNewsInDB(newsTitle, newsPostDate, linkToLastNewsFromCategory);

            Elements newsTextWithExtraSentences = lastNewsPageFromCategory.select(CSS_QUERY_WITH_NEWS_TEXT);

            if (newsTextWithExtraSentences == null) {
                throw new CustomException(TEXT_NEWS_ERROR);
            }

            StringBuilder clearNewsText = new StringBuilder();
            for (int i = 0; i < newsTextWithExtraSentences.size() - 1; i++) {
                String temp = newsTextWithExtraSentences.get(i).text();
                if (!temp.contains(EXTRA_SENTENCE_BEFORE_TEXT) && !temp.contains(EXTRA_SENTENCE_AFTER_TEXT))
                    clearNewsText.append(temp);
            }

            LinkedList<String> words = new LinkedList<>(Arrays.asList(clearNewsText.toString()
                    .replaceAll("[^a-zA-Zа-яА-Я0-9 _-]", " ")
                    .split(" ")));

            while (writingIsActive) {
                if (!words.isEmpty()) {
                    wordsBuffer.addWord(words.poll());
                } else {
                    writingIsActive = false;
                    writeThreadsFinished.incrementAndGet();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
