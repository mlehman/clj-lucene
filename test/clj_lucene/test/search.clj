(ns clj-lucene.test.search
  (:use [clj-lucene.search]
	[clj-lucene.index :only [add-document index-writer]]
	[clj-lucene.store :only [ram-directory]]
	[clj-lucene.test.data] :reload)
  (:use [clojure.test])
  (:import [org.apache.lucene.search IndexSearcher]))

(defn book-index-directory []
  (with-open [w (index-writer)]
    (doseq [b top-selling-books]
      (add-document w b))
    (.getDirectory w)))

(deftest test-index-searcher
  (testing "Creates a new IndexSearcher."
    (with-open [s (index-searcher {:directory (book-index-directory)})]
      (is (instance? IndexSearcher s))))
  (testing "Default opens directory."
    (with-open [s (index-searcher {:directory (book-index-directory)})]
      (is (= (count top-selling-books)
	     (.maxDoc s))))))


  
