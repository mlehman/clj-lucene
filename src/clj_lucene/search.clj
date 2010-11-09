(ns clj-lucene.search
  (:use [clj-lucene.index :only [term]])
  (:import [org.apache.lucene.search IndexSearcher TermQuery]
	   [org.apache.lucene.store RAMDirectory]
	   [org.apache.lucene.queryParser QueryParser]
	   [org.apache.lucene.analysis.standard StandardAnalyzer]
	   [org.apache.lucene.util Version]))

(defn ^IndexSearcher index-searcher
  "Constructs an IndexSearcher with a configuration map.
     :directory - Directory to open with the IndexReader.
     :read-only - If true, the underlying IndexReader will be opened read only. Default is false."
  [config]
  (let [directory (get config :directory)
	readOnly (get config :read-only false)]
    (IndexSearcher. directory readOnly)))

(defn query-parser
  "Constructs a QueryParser."
  []
  (QueryParser. Version/LUCENE_30 "*" (StandardAnalyzer. Version/LUCENE_30)))

(defn parse-query
  "Parses a string into Query."
  [q]
  (.parse (query-parser) q))
			
(defn search
  "Finds the hits for query. Optional parameters:
     :limit - Maximim hits to return. Defaults to 10."
  [^IndexSearcher index-searcher q & {:keys [limit] :or {limit 10}}]
  {:total-hits (.totalHits (.search index-searcher (parse-query q) limit))})

(defn ^TermQuery term-query
  "Constructs a TermQuery, a Query that matches documents containing a term."
  ([^String field] (TermQuery. (term field)))
  ([^String field ^String text] (TermQuery. (term field text))))