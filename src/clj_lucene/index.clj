(ns clj-lucene.index
  (:use [clj-lucene.document :only [document]])
  (:import [org.apache.lucene.index IndexWriter IndexWriter$MaxFieldLength]
	   [org.apache.lucene.store RAMDirectory]
	   [org.apache.lucene.analysis.standard StandardAnalyzer]
	   [org.apache.lucene.document Document Field Field$Store Field$Index] 
	   [org.apache.lucene.util Version]))

(defn index-writer
  "Constructs an IndexWriter."
  []
  (let [directory (RAMDirectory.)
	analyzer (StandardAnalyzer. Version/LUCENE_30)
	create true
	max-field-length (IndexWriter$MaxFieldLength. IndexWriter/DEFAULT_MAX_FIELD_LENGTH)]
    (IndexWriter. directory analyzer create max-field-length)))

(defn add-document
  "Adds a map representing the document to this index."
  [^IndexWriter w map]
  (.addDocument w (document map)))