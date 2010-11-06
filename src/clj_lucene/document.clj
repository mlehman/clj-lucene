(ns clj-lucene.document
  (:import [org.apache.lucene.document Document Field Field$Store Field$Index]))

(defn ^Document make-document
  "Converts a map into a Document."
  [map]
  (let [d (Document.)]
    (doseq [[k v] map]
      (.add d (Field. (name k) (str v) Field$Store/YES Field$Index/ANALYZED)))
    d))