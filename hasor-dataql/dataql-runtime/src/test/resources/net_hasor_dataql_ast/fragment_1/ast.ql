var dataSet = @@sql(itemCode,status) <% select * from category where co_code like '%:itemCode%' and status = :status %>

return dataSet("abc",true) => [
    {
        "id","name","code","body","params"
    }
]