#
# Change directory to ../main/resources/elasticsearch/ to execute following curl commands
# 

############################################

# Delete and create "people" index
curl -XDELETE http://localhost:9200/people
curl -XPUT http://localhost:9200/people/ -d @./setting/people-index-settings.json
curl -XPUT http://localhost:9200/people/_mapping/person/ -d @./mapping/map_person_5x_snake.json

# Delete and create "people-summary" index
curl -XDELETE http://localhost:9200/people-summary
curl -XPUT http://localhost:9200/people-summary/ -d @./setting/people-summary-index-settings.json
curl -XPUT http://localhost:9200/people-summary/_mapping/person-summary/ -d @./mapping/map_person_summary.json

# Delete and create "screenings" index
curl -XDELETE http://localhost:9200/screenings
curl -XPUT http://localhost:9200/screenings/ -d @./setting/screening-index-settings.json
curl -XPUT http://localhost:9200/screenings/_mapping/screening/ -d @./mapping/map_screening.json

############################################

# Preint people index
curl -u elastic -XDELETE http://elasticsearch.preint.cwds.io:9200/people
curl -u elastic -XPUT http://elasticsearch.preint.cwds.io:9200/people/ -d @./setting/people-index-settings.json
curl -u elastic -XPUT http://elasticsearch.preint.cwds.io:9200/people/_mapping/person/ -d @./mapping/map_person_5x_snake.json

# Preint people-summary index
curl -u elastic -XDELETE http://elasticsearch.preint.cwds.io:9200/people-summary
curl -u elastic -XPUT http://elasticsearch.preint.cwds.io:9200/people-summary/ -d @./setting/people-summary-index-settings.json
curl -u elastic -XPUT http://elasticsearch.preint.cwds.io:9200/people-summary/_mapping/person-summary/ -d @./mapping/map_person_summary.json