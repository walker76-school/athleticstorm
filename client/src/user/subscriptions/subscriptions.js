import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import React from "react";
import Grid from "@material-ui/core/Grid";
import makeStyles from "@material-ui/core/styles/makeStyles";

const useStyles = makeStyles({
    root: {
        maxWidth: 345,
    },
    media: {
        height: 140,
    },
});

class Subscriptions extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tiers: [{image: '/client/src/images/Logo.png', title: 'hey'}]
        };
    }

    componentDidMount() {
        // this.loadCurrentUser();
    }

    renderSubscriptions = () => {

        return this.state.tiers.map((card) => (
            <Card>
                <CardActionArea>
                    <CardMedia
                        image={card.image}
                        title={card.title}
                    />
                    <CardContent>
                        <Typography gutterBottom variant="h5" component="h2">
                            Lizard
                        </Typography>
                        <Typography variant="body2" color="textSecondary" component="p">
                            Lizards are a widespread group of squamate reptiles, with over 6,000 species, ranging
                            across all continents except Antarctica
                        </Typography>
                    </CardContent>
                </CardActionArea>
                <CardActions>
                    <Button size="small" color="primary">
                        Share
                    </Button>
                    <Button size="small" color="primary">
                        Learn More
                    </Button>
                </CardActions>
            </Card>
        ));
    };

    render() {
        return (
            <Grid.Row>
                <br/><br/><br/><br/><br/><br/>
                {this.renderSubscriptions()}
            </Grid.Row>
        );
    }
}

export default Subscriptions;
