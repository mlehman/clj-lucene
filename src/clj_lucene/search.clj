(ns clj-lucene.search
  (:import [org.apache.lucene.search IndexSearcher]
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

(defn parse-query
  ""
  [q]
  (.parse (QueryParser. Version/LUCENE_30 "*" (StandardAnalyzer. Version/LUCENE_30))
	  q))
			

(defn search
  "Finds the hits for query. Optional parameters:
     :limit - Maximim hits to return. Defaults to 10."
  [^IndexSearcher index-searcher q & {:keys [limit] :or {limit 10}}]
  {:total-hits (.totalHits (.search index-searcher (parse-query q) limit))})

  