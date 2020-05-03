# Description of Design Patterns

## State
Every front end component in our React app has an internal state.
This state is constantly updated when the is information being retrieved from the back end.
While loading the page, data is being retrieved.
Once it is completed, the front end component's state is updated and the data is displayed on the screen for the user.
React state is also used to search and filter our results for coach ratings, schools, etc.

## Observer
Every time the amount of teams a limited subscription user has remaining to view, they are given a notification of how many teams they have remaining.
When this number changes, the component that keeps track of a user's viewed teams gets notified and updated.
This happens as soon as the user selects a new team to view.

## Facade
A majority of our application is a look into the data of college football.
We possess this statistical data in our database, and the front end of our application is how user interact with and view our data.
Our front end can be seen as a facade into our statistical data in our database.

## Flyweight
Our coach rating algorithm is a heavy operation to calculate.
Because it would take a lot of calculating and processing if the score was calculated every time it is needed, we calculate it once on startup and place it in our database.
This way, scores can be quickly pulled from the database instead of being processed multiple times.

## Composite
In the back end of our application exists on object called RatingComposite which assists in calculating our coach's scores.
This object is used in the RatingService which does the processing for those ratings.