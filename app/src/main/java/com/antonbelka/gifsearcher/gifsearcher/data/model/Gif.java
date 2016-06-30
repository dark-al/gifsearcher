package com.antonbelka.gifsearcher.gifsearcher.data.model;

/**
 * Immutable model class for a Gif.
 */
public final class Gif {

    private String id;
    private String source;
    private String rating;
    private String import_datetime;
    private String trending_datetime;
    private Images images;

    public Gif(String id, String source, String rating, String import_datetime, String trending_datetime, Images images) {
        this.id = id;
        this.source = source;
        this.rating = rating;
        this.import_datetime = import_datetime;
        this.trending_datetime = trending_datetime;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getRating() {
        return rating;
    }

    public String getImportDate() {
        return import_datetime;
    }

    public String getTrendingDatetime() {
        return trending_datetime;
    }

    public String getUrl() {
        return images.fixed_height.url;
    }

    public String getWidth() {
        return images.fixed_height.width;
    }

    public String getHeight() {
        return images.fixed_height.height;
    }

    private final class Images {
        private FixedHeight fixed_height;

        private final class FixedHeight {
            private String url;
            private String width;
            private String height;
        }
    }
}
