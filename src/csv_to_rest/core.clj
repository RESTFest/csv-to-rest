(ns csv-to-rest.core
  (:gen-class))

(require '[clojure.data.csv   :as csv]
         '[clojure.java.io    :as io]
         '[clojure.pprint     :as pp]
         '[semantic-csv.core  :as sc :refer :all])


(defn blank?
  "Determines if a string is blank."
  [val]
  (or
    (nil? val)
    (empty? (clojure.string/trim val))))


(defn filter-empties
  "Omits map entries that have blank values."
  [maps]
  (map
    (fn [aMap]
      (apply dissoc aMap
        (map
          key
          (filter (fn [entry] (blank? (val entry))) aMap))))
    maps))


(defn csv-to-maps
  "Produces a seq of maps representing each row in the csv file."
  [filename]
  (with-open [in-file (io/reader filename)]
    (->> (csv/read-csv in-file)
         remove-comments
         mappify
         filter-empties
         doall)))


(defn distinct-cols
  "Given a seq of maps, create sets of distinct values for each key and return a map with those sets."
  [maps]
  (reduce
    (fn [acc aMap]
      (into {}
        (map
          (fn [[key, val]] [key (conj (get acc key #{}) val)])
          aMap)))
    {}
    maps))


(defn count-cols
  "Given a map of sets, create a map of size of the sets."
  [sets]
  (into {}
    (map (fn [[key val]] [key (count val)])
    sets)))


(defn sort-by-val
  "sort a map by the contents"
  [map]
  (into
    (sorted-map-by
      (fn [k1 k2]
        (compare
          (get map k1)
          (get map k2))))
    map))


(defn process-csv
  "process a file path that points to a csv"
  [file-path]
  (println "Processing" file-path)
  (-> (csv-to-maps file-path)
      distinct-cols
      count-cols
      sort-by-val))


(defn -main
  "Given a CSV file, outputs the distinct value counts for each column."
  [& args]
  (pp/pprint (process-csv (first args))))