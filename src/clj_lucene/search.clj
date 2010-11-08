(ns clj-lucene.search
  (:import [org.apache.lucene.search IndexSearcher]
	   [org.apache.lucene.store RAMDirectory]))

(defn ^IndexSearcher index-searcher
  "Constructs an IndexSearcher with a configuration map.
     :directory - Directory to open with the IndexReader.
     :read-only - If true, the underlying IndexReader will be opened read only. Default is false."
  [config]
  (let [directory (get config :directory)
	readOnly (get config :read-only false)]
    (IndexSearcher. directory readOnly)))