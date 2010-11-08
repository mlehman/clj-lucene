(ns clj-lucene.index
  (:use [clj-lucene.document :only [document]]
	[clj-lucene.store :only [ram-directory]])
  (:import [org.apache.lucene.index IndexWriter IndexWriter$MaxFieldLength]
	   [org.apache.lucene.store RAMDirectory]
	   [org.apache.lucene.analysis.standard StandardAnalyzer]
	   [org.apache.lucene.document Document Field Field$Store Field$Index] 
	   [org.apache.lucene.util Version]))

(defn ^IndexWriter index-writer
  "Constructs an IndexWriter with a configuration map.
     :directory - Directory to open with the IndexWriter. Default is a RAMDirectory.
     :create - If true, creates a new index. If false, will append to the existing index. Default is true."
  [& [config]]
  (let [directory (get config :directory (ram-directory))
	analyzer (StandardAnalyzer. Version/LUCENE_30)
	create (get config :create true)
	max-field-length (IndexWriter$MaxFieldLength. IndexWriter/DEFAULT_MAX_FIELD_LENGTH)]
    (IndexWriter. directory analyzer create max-field-length)))

(defn add-document
  "Adds a map representing the document to this index."
  [^IndexWriter w map]
  (.addDocument w (document map)))