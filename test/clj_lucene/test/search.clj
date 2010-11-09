(ns clj-lucene.test.search
  (:use [clj-lucene.search]
	[clj-lucene.index :only [add-document index-writer]]
	[clj-lucene.store :only [ram-directory]]
	[clj-lucene.test.data] :reload)
  (:use [clojure.test])
  (:import [org.apache.lucene.search IndexSearcher TermQuery]))

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

(deftest test-search
  (testing "Search"
    (with-open [s (index-searcher {:directory (book-index-directory)})]
      (is (= 1 (:total-hits (search s "author:Salinger" :limit 10))))
      (is (= 2 (:total-hits (search s "author:Tolkien" :limit 10))))
      (is (= 6 (:total-hits (search s "title:C*" :limit 10)))))))

(deftest testing-term-query
  (testing "Construct a TermQuery."
    (is (instance? TermQuery (term-query "author")))
    (is (instance? TermQuery (term-query "author" "Salinger"))))) 
  
