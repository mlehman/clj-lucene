(ns clj-lucene.test.search
  (:use [clj-lucene.search]
	[clj-lucene.index :only [add-document index-writer]]
	[clj-lucene.store :only [ram-directory]]
	[clj-lucene.test.data] :reload)
  (:use [clojure.test])
  (:import [org.apache.lucene.search IndexSearcher]))

(deftest test-index-searcher
  (testing "Creates a new IndexSearcher."
    (let [r (ram-directory)]
      (with-open [w (index-writer {:directory r})])
      (with-open [s (index-searcher {:directory r})]
	(is (instance? IndexSearcher s)))))
  (testing "Default opens directory."
    (let [r (ram-directory)]
      (with-open [w (index-writer {:directory r})]
	(doseq [b top-selling-books]
	  (add-document w b)))
      (with-open [s (index-searcher {:directory r})]
	(is (= (count top-selling-books)
	       (.maxDoc s)))))))
