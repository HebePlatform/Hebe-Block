----
# OPERATORS GUIDE #

----
## How to verify the NRS? ##
  Releases are signed by Jean-Luc using [GPG](https://en.wikipedia.org/wiki/GNU_Privacy_Guard). It is **highly** recommended to verify the signature every time you download new version. [There are some notes](https://bitcointalk.org/index.php?topic=345619.msg4406124#msg4406124) how to do this. 

----
## How to configure the NRS? ##

  - config files under `conf/`
  - options are described in config files
  - **do not edit** `conf/hebe-default.properties` **nor** `conf/logging-default.properties`
  - use own config file instead: `conf/hebe.properties` or `conf/logging.properties`
  - only deviations from default config

----
## How to update the NRS? ##

  - **if configured as described above**, just unpack a new version over the existing installation directory
  - next run of NRS will upgrade database if necessary
  

----