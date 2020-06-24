<html>
    <head>
        <title>Inventory New Items</title>
    </head>
    <body>
        <h2>inventory Management - New Items</h2>
        <hr />
        
        
        <form action="confirmAddItem" method="POST">
			<div>
				<label>Item Code: </label>
				<input type="number" name="icode" required> 
			</div>
			<div>
				<label>Title: </label>
				<input type="text" name="title" required>
				
			</div>   
			<div>
				<label>Price:</label>
				<input type="decimal" name="price" min="100.0" max="5500.0" required/>
				
			</div>  
			<div>
				<label>Package Date:</label>
				<input type="date" name="packageDate" required />
				
			</div>
			<div>
				<button>Add Item</button>
			</div>
        </form>
       
    </body>
</html>