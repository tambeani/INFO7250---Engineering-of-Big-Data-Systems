### MongoDB in Action (2nd Edition): Chapter 7 - Updates & Deletes

1. Updating document
2. Processing documents atomically
3. Using update operators
4. Deleting documents

### Terms & their definition:
1. Update: Write to existing documents
2. Atomicity: In this case atomicity means ability of MongoDB to find and update it, with the guarantee that no other operation will interfere, a powerful property.
3. Denormalization: Used for combining multiple table data into one, mainly to introduce redundancy.

### Two ways of updating in MongoDB:
1. Replace
2. Update operator(for specific fields)

### Modify by Replacement

To update by replacement, we need to query the document, update it on client side, and then issue the update with the modified document.

MongoDB shell script for updating email:
```
doc = db.users.findOne({_id:ObjectId("4c4b1476238d3b4dd5003981")});
doc['email'] = "test@xyx.com";
db.users.update({_id:ObjectId("4c4b1476238d3b4dd5003981")},doc);
```

While interpreting the last command, it says,

*"Find the document in users collection with given _id and replace that document with the one provided."*

### Modify by operators

Let's update the email address using an update operator,
```
user_id = ObjectId("4c4b1476238d3b4dd5000001")
db.users.update({_id:user_id},{$set:{email:"test@xyx.com"}});
```
This update request, as compared to update by replacement, is much more targeted. Hence, they can be named as targeted updates as well.

Syntax of targeted queries:
```
db.collection.update(
    {}, --> query
    { operator: { tag: value }} --> operator for update
);
```

Exercise 1: Add tag "cheap" for products with price less than or equal to 10
```
db.products.update({price: {$lte:10}},{$set:{tag:"cheap"}});
```

Here, the query has a query operator which is semantically adjectival and comes after the field name in the query.

> Note: Query operator follow the infix notation (i.e. comes later) and Update operators follow the prefix notation (i.e comes before).

### Comparing both methods

Exercise 2: Increment the reviews on a product using both methods

Via replacement:
```
product_id = ObjectID("4c4b1476238d3b4dd5003982");
doc = db.products.findOne({ _id:product_id });
doc['total_reviews'] += 1;
db.products.update({_id:product_id},doc);
```

Via update operators:
```
product_id = ObjectID("4c4b1476238d3b4dd5003982");
db.products.update(
    {_id:product_id},
    {$inc: {total_reviews:1}}
);
```

Comparing the 2 methods we can say that,

1. Update by replacement is a generic method and is suitable for applications with models of arbitrary complexity
2. Targeted modification yields better results because,
 - Eliminate the need to fetch the document from the server
 - Document specifying the update is smaller in size(> 100 bytes)
 - Ensures atomicity (optimistic locking)

### Optimistic locking

It is a technique used for ensuring a clean update to a record, without locking it. When a user tries to save their changes, a timestamp is included in the attempted update. If that timestamp is older than the latest saved version, the update is not allowed.

The opposite of this approach is pessimistic locking, the accessed record is locked in a transaction until that transaction is finished.

### E-commerce model updates

Exercise 3: How to calculate average product ratings?
```
total = 0;
count = 0;
product_id = ObjectId("4c4b1476238d3b4dd5003981")

db.reviews.find({product_id:product_id}).forEach(
    function(review){
        total += review.rating;
        count++;
    }
)

average_rating = total/count;

db.reviews.update(
    {product_id:product_id},
    {$set:
        {
            total_reviews: count,
            average_rating:average_rating
        }
    }
);
```

### Category hierarchy

Exercise 4: Write a script to update an ancestor list for any category
```
var generate_ancestors = function(_id,parent_id){
    ancestor_list = [];
    var cursor = db.categories.find({_id:parent_id});
    while(cursor.size() > 0){
        parent = cursor.next();
        ancestor_list.push(parent);
        parent_id = parent.parent_id;
        cursor = db.categories.find({_id:parent_id});
    }
    db.categories.update({_id:_id},{$set:{ancestors:ancestor_list}});
}
```

Exercise 5: Insert a category of gardening and place it under the Home category.
```
parent_id = ObjectId("8b87fb1476238d3b4dd50003")
category = {
    parent_id : parent_id,
    name: "Gardening"
}

db.categories.save(category);
generate_ancestors(category._id,parent_id);
```

Exercise 6: Replace the category Outdoors with The outdoors.
```
doc = db.categories.findOne({_id: outdoors_id})
doc.name = "The outdoors";
db.categories.update({_id: outdoors_id},doc);
db.categories.update(
    {'ancestors._id':outdoors_id},
    {$set : {'ancestors.$' : doc}},
    {multi: true}
    );
```

Here, the query fetches the matched indexes ( viz all the old outdoor instances) and using the positional operator we reference that index and replace with the replaced document. This technique is very useful when updating subarray elements within a document.

> Positional operator: $

| Operators   | Description |
| ----------- | ----------- |
| $set      | Title       |
| $addToSet   | Text        |
| $lte   | Text        |
$inc
$push
$ne
$pull
$pullAll
$addToSet
$unset
$rename
$setOnInsert
$each
$slice
$sort
$pop
$bit
$isolated
$atomic
