package app.service;

import app.service.interfaces.NewsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import static app.utils.MyConstants.*;

public class Parser {

    private NewsService newsService;
    private WordsBuffer wordsBuffer;
    public static boolean isActive = true;

    public Parser(NewsService newsService, WordsBuffer wordsBuffer) {
        this.newsService = newsService;
        this.wordsBuffer = wordsBuffer;
    }

    public void parseAndSave(String category) {

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
                Elements newsTextWithOneExtraSentencesBeforeAndOneAfter = LastNewsPageFromCategory.select(CSS_QUERY_WITH_NEWS_TEXT);
                for (int i = 1; i < newsTextWithOneExtraSentencesBeforeAndOneAfter.size() - 2; i++) {
                    clearNewsText.append(newsTextWithOneExtraSentencesBeforeAndOneAfter.get(i).text());
                }

                LinkedList<String> words = new LinkedList<>();
                words.addAll(Arrays.asList(clearNewsText.toString()
                        .replaceAll("[^a-zA-Zа-яА-Я0-9 _-]", "")
                        .split(" ")));

                while (isActive) {
                    if (!words.isEmpty()) {
                        wordsBuffer.addWord(words.poll());
                    } else {
                        isActive = false;
                    }
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
    }
}
