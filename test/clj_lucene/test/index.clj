(ns clj-lucene.test.index
  (:use [clj-lucene.index]
	[clj-lucene.test.data :only [top-selling-books]] :reload)
  (:use [clojure.test])
  (:import [org.apache.lucene.index IndexWriter Term]))

(deftest test-index-writer
  (testing "Default creates a writer with a RAM Directory."
    (with-open [w (index-writer)]
      (is (instance? IndexWriter w)))))

(deftest test-index-add-document
  (testing "Default indexes all fields."
    (with-open [w (index-writer)]
      (doseq [b top-selling-books]
	(add-document w b))
      (is (= (count top-selling-books)
	     (.numDocs w))))))

(deftest test-term
  (testing "Constructs a term."
    (is (instance? Term (term "author")))
    (is (instance? Term (term "author" "Salinger")))))
