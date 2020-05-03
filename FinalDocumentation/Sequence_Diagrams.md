# Sequence Diagrams

## View School

In this diagram, the Observer pattern is used to update the amount of schools a user has remaining to view.
When a user selects a school to view, the front end notifies the component that keeps track of the list of schools a user has viewed and which subscription tier they are. 

![View SchoolSequenceDiagram](..\documentation\diagrams\sequence\ViewSchoolSequenceDiagram.png)

### View Schools

This diagram involves the front end component's state being updated while the page loads for the user.
React initiates loading the page such as the header, footer, and search bar information while the information is retrieved from the backend.
When the routine retrieving the schools has finished, the state of the component is updated and the schools are displayed to the user.

![ViewSchoolsSequenceDiagram](..\documentation\diagrams\sequence\ViewSchoolsSequenceDiagram.PNG)

### Choose Subscription

![ChooseSubscriptionSequenceDiagram](..\documentation\diagrams\sequence\ChooseSubscriptionSequenceDiagram.png)

### Login

![LoginSequenceDiagram](..\documentation\diagrams\sequence\LoginSequenceDiagram.png)

### View Team/School Page

![ViewTeamProfileSequenceDiagram](..\documentation\diagrams\sequence\ViewTeamProfileSequenceDiagram.PNG)

### View Coach Profile

![ViewCoachesProfileSequenceDiagram](..\documentation\diagrams\sequence\ViewCoachesProfileSequenceDiagram.PNG)

### View Coach Ratings

![ViewCoachRatingsSequenceDiagram](..\documentation\diagrams\sequence\ViewCoachRatingsSequenceDiagram.png)