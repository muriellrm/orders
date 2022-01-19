let cursor = db.orders.aggregate(
    [
        {
            "$unwind": "$items"
        },
        {
            "$group": {
                "_id": "$items.productId",
                "amount": {
                    "$sum": "$items.amount"
                },
                "quantity": {
                    "$sum": "$items.quantity"
                },
                "lastOrderDate": { $max: "$createdDate" }
            }
        },
        {
            "$project": {
                "_id": 0,
                "amount": 1,
                "quantity": 1,
                "lastOrderDate": 1,
                "productId": "$_id"
            }
        }
    ]
);

while (cursor.hasNext()) {
    printjson(cursor.next());
}