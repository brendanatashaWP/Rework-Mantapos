<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
    <!--BOOTSTRAP-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <%--link ke css local--%>
    <link rel="stylesheet" href="../../resources/css/main.css"/>
</head>
<body>
    <div class="row">
        <div class="col-md-3">
        </div>
        <div id="receipt" class="col-md-6">
            <div>
                <div>
                    <div class="receipt-header">
                        <h4 id="restaurantNameReceipt" class="topModal" style="text-align:center">${restaurant_name}</h4>
                        <h4 id="restaurantAddressReceipt" style="text-align: center; font-weight: 200">${restaurant_address}</h4>
                        <h5 id="custNameReceipt" style="padding-top: 10px; font-weight: 200">${customer_name}</h5>
                    </div>
                    <div>
                        <div class="form-group">
                            <table id="tableReceipt" class="table table-striped">
                                <thead>
                                <tr>
                                    <th>Menu</th>
                                    <th>Qty</th>
                                    <th>Total Price</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                        <h4 id="priceTotalReceipt">Total :  </h4>
                        <h4 id="CashReceipt">Cash :</h4>
                        <h4 id="ChangeReceipt">Change due: </h4>
                        <div>
                        </div>
                    </div>
                    <div>
                        <%--<button type="button" class="submit" data-dismiss="modal">OK</button>--%>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="../../resources/js/order.js"></script>
</body>
</html>