# Configuring Settings for a Corpus

## Generating Example Queries

User created example queries are stored in the file `example_queries.annis` within the
relANNIS corpus folder. The file contains two columns (tab delimited), the first with a
valid AQL query for your corpus and the second with a human readable description of
the query. These queries are the then visible in Example Queries tab of the workspace
on the right side of the ANNIS interface.

It is also possible to have ANNIS automatically generate queries for a corpus (instead
of, or in addition to user created examples). ANNIS will then create some randomized,
typical queries, such as searches for a word form appearing in the corpus or a regular
expression. To determine whether or not example queries are generated by default,
change the following setting in `annis-service.properties`:

~~~inis
annis.import.example-queries=false
~~~

On an ANNIS server console it is also possible to generate new example queries on
demand, replacing or adding to existing queries, and to delete queries for individual
corpora. For more information on the exact commands and options see the help under:

~~~bash
bin/annis-admin.sh --help
~~~

## Setting Default Context and Segmentations

In corpora with multiple segmentations, such as historical corpora with conflicting
diplomatic and normalized word form layers, it is possible to choose the default
segmentation for both search context and the KWIC visualization. To set the relevant
segmentations, use the following settings in the `corpus.properties` file in the folder
`ExtData` within the relANNIS corpus:

~~~ini
default-context-segmentation=SEGNAME
default-base-text-segmentation=SEGNAME
~~~

For more details on segmentations, see the ANNIS Multiple Segmentation Corpora
Guide.