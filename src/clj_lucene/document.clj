(ns clj-lucene.document
  (:import [org.apache.lucene.document Document Field Fieldable Field$Store Field$Index NumericField]
	   [org.apache.lucene.util NumericUtils]))

(defn ^String field-name
  "Converts an object to a valid field name."
  [k]
  (if (keyword? k)
    (name k)
    (str k)))

(defn default-field-type
  [x]
  (if (number? x)
    (if (integer? x)
      :long
      :double)
    :string))

(defn ^Fieldable field
  "Constructs a Field."
  [k v fs]
  (let [ft (get fs :type (default-field-type v))
	n (field-name k)
	store (get fs :store Field$Store/YES)
	index (get fs :index  Field$Index/ANALYZED)]
    (if (= ft :string)
      (Field. n (str v) store index)
      (let [precision-step (get fs :precision-step NumericUtils/PRECISION_STEP_DEFAULT)
	    nf (NumericField. n store true)]
	(cond (= :integer ft) (.setIntValue nf (int v))
	      (= :long ft) (.setLongValue nf (long v))
	      (= :float ft) (.setFloatValue nf (float v))
	      (= :double ft) (.setDoubleValue nf (double v)))
	nf))))
  
(defn ^Document document
  "Converts a map into a Document."
  ([map] (document map nil))
  ([map schema]
     (let [d (Document.)]
       (doseq [[k v] map]
	 (.add d (field k v (get schema k))))
       d)))