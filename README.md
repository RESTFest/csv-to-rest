#CSV To REST

# Can we make a CSV navigable with a UI and REST API?

Search is OK, but not hard to see patterns and relationships


## THEORY: CSV data is often a Directed Graph, or Hierarchical

* State, City, Zip, address1, address2
* County, Year, Crime Code


### Can we infer the graph?

# Hierarchy
Copied fields are collections, like "State" has hundreds of "SC" values.

/states
/states/SC
/states/SC/cities/Greenville


### Create a new UX

### Generate a strucutre from CSV

To use the csv distinct counter tool, one needs to install [Leiningen](https://leiningen.org), a Clojure CLI tool.
Once Leiningen is installed, exec the tool with this command:
`lein run path/to/csv-file.csv`

### Generate an API from the CSV
