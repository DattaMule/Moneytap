package com.example.muledattatraya.moneytap.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchData implements Serializable {

    @SerializedName("batchcomplete")
    private boolean keyBatchcomplete;

    @SerializedName("query")
    private Query keyQuery;

    @SerializedName("continue")
    private Continue keyContinue;

    public Query getQuery() {
        return keyQuery;
    }

    public void setQuery(Query query) {
        this.keyQuery = query;
    }

    public Continue getKeyContinue() {
        return keyContinue;
    }

    public void setKeyContinue(Continue keyContinue) {
        this.keyContinue = keyContinue;
    }

    public boolean isBatchcomplete() {
        return keyBatchcomplete;
    }

    public void setBatchcomplete(boolean keyBatchcomplete) {
        this.keyBatchcomplete = keyBatchcomplete;
    }

    public class Query {
        @SerializedName("pages")
        private List<Pages> keyPages;

        public List<Pages> getPages() {
            return keyPages;
        }

        public void setPages(List<Pages> pages) {
            this.keyPages = pages;
        }

        @Override
        public String toString() {
            return "ClassPojo [pages = " + keyPages + "]";
        }
    }

    public class Pages {
        @SerializedName("pageid")
        private String keyPageid;

        @SerializedName("ns")
        private int keyNS;

        @SerializedName("title")
        private String keyTitle;

        @SerializedName("index")
        private int keyIndex;

        @SerializedName("thumbnail")
        private Thumbnail keyThumbnail;

        @SerializedName("terms")
        private Terms keyTerms;


        public int getIndex() {
            return keyIndex;
        }

        public void setIndex(int keyIndex) {
            this.keyIndex = keyIndex;
        }

        public String getTitle() {
            return keyTitle;
        }

        public void setTitle(String keyTitle) {
            this.keyTitle = keyTitle;
        }

        public int getNs() {
            return keyNS;
        }

        public void setNs(int keyNS) {
            this.keyNS = keyNS;
        }

        public Thumbnail getThumbnail() {
            return keyThumbnail;
        }

        public void setThumbnail(Thumbnail keyThumbnail) {
            this.keyThumbnail = keyThumbnail;
        }

        public Terms getTerms() {
            return keyTerms;
        }

        public void setTerms(Terms keyTerms) {
            this.keyTerms = keyTerms;
        }

        public String getPageid() {
            return keyPageid;
        }

        public void setPageid(String keyPageid) {
            this.keyPageid = keyPageid;
        }

        @Override
        public String toString() {
            return "ClassPojo [index = " + keyIndex + ", title = " + keyTitle + ", ns = " + keyNS + ", thumbnail = " + keyThumbnail + ", terms = " + keyTerms + ", pageid = " + keyPageid + "]";
        }
    }

    public class Terms {
        @SerializedName("description")
        private List<String> keyDescription;

        public List<String> getDescription() {
            return keyDescription;
        }

        public void setDescription(List<String> description) {
            this.keyDescription = description;
        }

        @Override
        public String toString() {
            return "ClassPojo [description = " + keyDescription + "]";
        }
    }

    public class Thumbnail {
        @SerializedName("source")
        private String keySource;

        @SerializedName("width")
        private String keyWidth;

        @SerializedName("height")
        private String height;

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getSource() {
            return keySource;
        }

        public void setSource(String keySource) {
            this.keySource = keySource;
        }

        public String getWidth() {
            return keyWidth;
        }

        public void setWidth(String width) {
            this.keyWidth = width;
        }

        @Override
        public String toString() {
            return "ClassPojo [height = " + height + ", source = " + keySource + ", width = " + keyWidth + "]";
        }
    }

    public class Continue {
        @SerializedName("gpsoffset")
        private String keyGpsoffset;

        @SerializedName("continue")
        private String keyContinue;

        public String getKeyGpsoffset() {
            return keyGpsoffset;
        }

        public void setKeyGpsoffset(String keyGpsoffset) {
            this.keyGpsoffset = keyGpsoffset;
        }

        public String getKeyContinue() {
            return keyContinue;
        }

        public void setKeyContinue(String keyContinue) {
            this.keyContinue = keyContinue;
        }
    }
}
