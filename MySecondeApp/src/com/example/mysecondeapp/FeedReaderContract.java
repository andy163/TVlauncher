package com.example.mysecondeapp;

import android.provider.BaseColumns;

public class FeedReaderContract {
	// To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        public static final String COLUMN_NAME_CONTENT = "content";
    }
    
    public class FeedEntryBean extends FeedEntry  {
        private String entryId;
        private String title;
        private String subtitle;
        private String content;
        
		public FeedEntryBean() {
			super();
		}
		
		public FeedEntryBean(String entryId, String title, String subtitle,
				String content) {
			super();
			this.entryId = entryId;
			this.title = title;
			this.subtitle = subtitle;
			this.content = content;
		}
		
		public String getEntryId() {
			return entryId;
		}
		public void setEntryId(String entryId) {
			this.entryId = entryId;
		}
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		
		public String getSubtitle() {
			return subtitle;
		}
		public void setSubtitle(String subtitle) {
			this.subtitle = subtitle;
		}
		
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
        
    }
}
