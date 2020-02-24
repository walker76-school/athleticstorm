import React from 'react'
import Subscription from './subscription'
import './styles/comparisons.css'
import { makeStyles } from '@material-ui/core/styles';
import CheckIcon from '@material-ui/icons/Check';
import tier1 from './images/tier1.jpeg'
import tier2 from './images/tier2.jpg'
import tier3 from './images/tier3.jpg'

// const useStyles = makeStyles({
//     product_comparisons: {
//         table: {
//             backgroundColor: '#fff',
//             borderRadius: '5px',
//             overflow: 'hidden',
//             boxShadow: '0 13px 21px -5px rgba(0, 50, 0, 0.05)',
//             border: '1px solid #eee',
//             fontSize: '18px',
//             tableLayout: 'fixed',
//             tr: {
//                 th: {
//                     paddingTop: '25px',
//                     paddingBottom: '25px',
//                     textAlign: 'center'
//                 },
//
//         }
//     },
//  }
// .product_comparisons table tr > td,
// .product_comparisons table tr > th {
//      }
// .product_comparisons table th[scope="row"] {
//     font-size: 20px;
//     color: #393c45;
//     font-weight: normal;
//     background-color: #f9f9f9;
//     border: 1px solid #eee;
//     text-align: left;
//     padding-left: 45px; }
// .product_comparisons table tr.condition {
//     color: #000000;
//     font-size: 20px; }
// .product_comparisons table tr.colors span {
//     height: 20px;
//     width: 20px;
//     display: inline-block;
//     margin-right: 5px;
//     border-radius: 100%; }
// .product_comparisons table thead th {
//     font-size: 35px;
//     color: #393c45;
//     font-weight: normal; }
//
// .product_comparisons .thead-default th {
//     background-color: #fff; }
//
// });

class Subscription_List extends React.Component {



    constructor(props) {
        super(props);

        this.state = {
            tiers: [{image: tier1, name: 'Redshirt', price: '$4.99/month', id: 1, numTeams: 10, adv: false},
                {image: tier2, name: 'Starter', price: '$9.99/month', id: 2, numTeams: 20, adv: true},
                {image: tier3, name: 'MVP', price: '$14.99/month', id: 3, numTeams: 'All', adv: true}]
        };
    }

    render() {
        // const classes = useStyles();

        return(

        <div className="row mt-3">
            {this.state.tiers.map(tier =>
                <Subscription key={tier.id} id={tier.id} image={tier.image} name={tier.name} price={tier.price}
                              description={tier.description}/>
            )}
            <div className="row product_comparisons">
                <div className="col-12 mt-5 text-center">
                    <table className="table">
                        <thead className="thead-default">
                        <tr>
                            <th />
                            {this.state.tiers.map(tier =>
                                <th key={tier.id}>
                                    {tier.name}
                                </th>
                            )}
                        </tr>
                        </thead>
                        <tbody>
                        <tr className="price">
                            <th scope="row">Price</th>
                            {this.state.tiers.map(tier =>
                                <td key={tier.id} className="text-center">{tier.price}</td>
                            )}
                        </tr>
                        <tr className="description">
                            <th scope="row">Number of Teams Accessible</th>
                            {this.state.tiers.map(tier =>
                                <td key={tier.id}>
                                    {tier.numTeams}
                                </td>
                            )}
                        </tr>
                        <tr className="description">
                            <th scope="row">Access to advanced metrics</th>
                            {this.state.tiers.map(tier =>
                                <td key={tier.id}>
                                    {tier.adv && <CheckIcon color="primary"/>}
                                </td>
                            )}
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        );
    }
}

export default Subscription_List