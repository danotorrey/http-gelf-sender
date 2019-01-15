# http-gelf-sender
A simple Java application that sends HTTP GELF messages to Graylog.

Customize the fields in Main.buildJson() to send your own data to Graylog. Make sure to include an underscore before each custom field name eg. `_a_new_field`. This tells Graylog that you are supplying a custom field.

See [this doc](http://docs.graylog.org/en/2.5/pages/gelf.html#gelf-payload-specification) for more information about the GELF payload specification.)
