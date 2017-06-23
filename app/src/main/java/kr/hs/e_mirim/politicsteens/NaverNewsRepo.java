package kr.hs.e_mirim.politicsteens;

import java.util.List;

/**
 * Created by User on 2017-06-21.
 */

public class NaverNewsRepo {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<Item> items;

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public int getTotal() {
        return total;
    }

    public int getStart() {
        return start;
    }

    public int getDisplay() {
        return display;
    }

    public List<Item> getItems() {
        return items;
    }

    public static class Item{
        private String title;
        private String originallink;
        private String link;
        private String description;
        private String pubDate;

        public String getTitle() {
            return title;
        }

        public String getOriginallink() {
            return originallink;
        }

        public String getLink() {
            return link;
        }

        public String getDescription() {
            return description;
        }

        public String getPubDate() {
            return pubDate;
        }

    }
}

