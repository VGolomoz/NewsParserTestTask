package app.service;

import app.service.interfaces.NewsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import static app.utils.MyConstants.*;

public class Parser {

    private NewsService newsService;

    public Parser(NewsService newsService) {
        this.newsService = newsService;
    }

    public void start(List<String> categories) {
        categories.forEach(this::parseAndSave);
    }

    private void parseAndSave(String category) {
        new Thread(() -> {
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

                StringBuilder clearNewsText = new StringBuilder();
                Elements newsTextWithOneExtraSentencesBeforeAndOneAfter = LastNewsPageFromCategory.select(CSS_QUERY_WITH_NEWS_TEXT);
                for (int i = 1; i < newsTextWithOneExtraSentencesBeforeAndOneAfter.size() - 2; i++) {
                    clearNewsText.append(newsTextWithOneExtraSentencesBeforeAndOneAfter.get(i).text());
                }

                String fileNameForSaveNewsText = UUID.randomUUID() + ".txt";

                if (newsService.createNewsInDB(newsTitle, newsPostDate, fileNameForSaveNewsText)) {
                    FileWriter writer = new FileWriter(fileNameForSaveNewsText, false);
                    writer.write(clearNewsText.toString());
                    writer.flush();
                    writer.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
