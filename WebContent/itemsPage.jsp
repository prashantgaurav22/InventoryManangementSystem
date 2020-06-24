<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<html>
    <head>
        <title>Inventory Items</title>
    </head>
    <body>
        <h2>Inventory Management - Items List</h2>
        <hr />
        
        <c:choose>
            <c:when test="${items eq null }">
                <p><strong>No Items Found!</strong>
            </c:when>
            <c:when test="${items.size() eq 0 }">
                <p><strong>No Items Found!</strong>
            </c:when>
            <c:otherwise>
                
                <table border="1" width="100%">
                    <tr>
                        <th>Item Code</th>
                        <th>Title</th>
                        <th>Price</th>
                        <th>Date Of Package</th>
                    </tr>
                    <c:forEach items="${items }" var="item">
                        <tr>
                            <td>${item.icode }</td>
                            <td>${item.title }</td>
                            <td>${item.price }</td>
                            <td>${item.packageDate }</td>
                            <td><a href="deleteItem?icode=${item.icode}">DELETE</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
        
        <hr />
        <nav>
            <ul>
                <li><a href="index.jsp">Home</a></li>
            </ul>
        </nav>
    </body>
</html>