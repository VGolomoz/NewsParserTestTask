package app.service;

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

public class Parser {

    private NewsService newsService;
    private WordsBuffer wordsBuffer;
    public static AtomicInteger writeThreadsFinished = new AtomicInteger(0);


    public Parser(NewsService newsService, WordsBuffer wordsBuffer) {
        this.newsService = newsService;
        this.wordsBuffer = wordsBuffer;
    }

    public void start(){

        List<String> categories = new ArrayList<>();
        categories.add(POLITICS_CATEGORY);
        categories.add(WAR_CATEGORY);
        categories.add(SOCIETY_CATEGORY);
        categories.add(WORLD_CATEGORY);
        categories.add(HEALTH_CATEGORY);

        categories.forEach(c -> {
            new Thread (() -> parseAndSave(c)).start();
        });
    }

    private void parseAndSave(String category) {

        boolean isActive = true;

            try {
                Document categoryPage = Jsoup
                        .connect(category)
                        .userAgent(USER_AGENT)
                        .validateTLSCertificates(false)
                        .get();

                String linkToLastNewsFromCategory = categoryPage
                        .select(CSS_QUERY_WITH_LAST_PUBLICATIONS)
                        .select("a[href]")
                        .get(0).attr("href");

                Document LastNewsPageFromCategory = Jsoup.connect(linkToLastNewsFromCategory)
                        .userAgent(USER_AGENT)
                        .validateTLSCertificates(false)
                        .get();

                String newsTitle = LastNewsPageFromCategory.title();
                String newsPostDate = LastNewsPageFromCategory
                        .select(CSS_QUERY_WITH_NEWS_POST_DATE)
                        .first()
                        .text();

                newsService.createNewsInDB(newsTitle, newsPostDate, linkToLastNewsFromCategory);

                StringBuilder clearNewsText = new StringBuilder();
                Elements newsTextWithExtraSentences = LastNewsPageFromCategory.select(CSS_QUERY_WITH_NEWS_TEXT);

                for (int i = 0; i < newsTextWithExtraSentences.size()-1; i++) {
                    String temp = newsTextWithExtraSentences.get(i).text();
                    if(!temp.contains(EXTRA_SENTENCE_BEFORE_TEXT) && !temp.contains(EXTRA_SENTENCE_AFTER_TEXT))
                        clearNewsText.append(temp);
                }

                LinkedList<String> words = new LinkedList<>(Arrays.asList(clearNewsText.toString()
                        .replaceAll("[^a-zA-Zа-яА-Я0-9 _-]", " ")
                        .split(" ")));

                words.removeIf(w -> w.equals(""));

                while (isActive) {
                    if (!words.isEmpty()) {
                        wordsBuffer.addWord(words.poll());
                    } else {
                        isActive = false;
                        writeThreadsFinished.incrementAndGet();
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
    }
}
