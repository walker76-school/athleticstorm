## Gotchas And Lessons Learned

- The third part API was unreliable
- Transactional data population can make debugging difficult
- Plan for needing data in your own database
- Don't rely solely on third party APIs for data
- Double check implementation of algorithms since it's easy to make transpositional mistakes
- Always validate that you have data before using it when retrieving from the database, especially when data is spott , like from the third party API
- Overestimate the amount of data you'll need to store and plan ahead (i.e. storing all years of coaches and team rosters and not just the current)