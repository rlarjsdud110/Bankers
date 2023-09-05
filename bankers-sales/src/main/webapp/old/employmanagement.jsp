<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<!DOCTYPE html>

<html lang="en">

<head>

	<meta charset="UTF-8">

	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<title>Header</title>

	<link rel="stylesheet" href="../css/body.css">
	<link rel="stylesheet" href="../css/footer.css">
	<link rel="stylesheet" href="../css/hearder.css">
	<link rel="stylesheet" href="../css/sidebar.css">
	<link rel="stylesheet" href="../css/style.css">
	<link rel="stylesheet" href="../css/employeemanagent.css">
	<link rel="icon" type="image/x-icon" href="../images/favicon/favicon.ico">
	<script>
    function addRow() {
        // ...
        // (���� �ڵ�)
        
        // ������ ������ �����Ͽ� DB�� ����
        saveDataToDB(newNum, newName, newPhoneNumber, newID, newPW, newStatus);
    }
    
    function saveDataToDB(num, name, phoneNumber, id, pw, status) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                console.log(this.responseText); // ���� ���� Ȯ��
            }
        };
        
        var url = "http://localhost:8080/bankersemp"; // ���� ������ ó�� URL�� �����ؾ� ��
        var params = "num=" + num + "&name=" + name + "&phoneNumber=" + phoneNumber + "&id=" + id + "&pw=" + pw + "&status=" + status;
        xhttp.open("POST", url, true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send(params);
    }
	</script>
</head>

<body>

	<div class="container">

		<div class="head">

			<h1>
				BANKERS | ������
			</h1>
			<nav>
				<ul>
					<li>
						<a>USER</a>
					</li>
					<li>
						<a href="user.html">�α׾ƿ�</a>
					</li>
				</ul>
			</nav>
		</div>
		<div class="sidebar">
			<div class="btn-group">
				<button onclick="window.location.href='mainpage.html'">����</button>
				<button onclick="window.location.href='newcustumer.html'">�ű� ��</button>
				<button onclick="window.location.href='customermanagement.html'">���� �� ����</button>
			</div>
		</div>
		<div class="body">
			<h2>���� ��� ����</h2>
			<div class="scroll-container">
			
<!-- �������� �ϳ��ϳ� ������ ���� -->
				<table class="scrolltable">
					<thead>
						<td><input type="checkbox" class="check"></td>
						<th scope="col">��ȣ</th>
						<th scope="col">�̸�</th>
						<th scope="col">��ȭ��ȣ</th>
						<th scope="col">ID</th>
						<th scope="col">PW</th>
						<th scope="col">����</th>
						<th scope="col">����</th>
						<th scope="col">��ư1</th>
						<th scope="col">��ư2</th>
						<th scope="col">��ư3</th>
					</thead>
					<tbody>
						<tr>
							<td><input type="checkbox" class="check"></td>
							
							<td>${Set1Num}</td>

							<td>${Set1Name}</td>

							<td>${Set1PhoneNumber}</td>

							<td>${Set1ID}</td>
							<td>${Set1PW}</td>
							<td>����(notyet)</td>
							<td>
							<%
							    String status1 = (String)request.getAttribute("Set1Status");
							
							    String status1Text = "";
							    if ("admin".equals(status1) || "emp".equals(status1)) {
							        status1Text = "����";
							    } else {
							        status1Text = "����";
							    }
							%>
						    <%= status1Text %>
							</td>
							<td><button>����</button></td>
							<td><button onclick="window.location.href='employeerevenue.html'">����</button></td>
							<td><button>��Ȱ��ȭ</button></td>
						</tr>
						
						<tr>
							<td><input type="checkbox" class="check"></td>
							
							<td>${Set2Num}</td>

							<td>${Set2Name}</td>

							<td>${Set2PhoneNumber}</td>

							<td>${Set2ID}</td>
							<td>${Set2PW}</td>
							<td>����(notyet)</td>
							<td>
							<%
							    String status2 = (String)request.getAttribute("Set2Status");
							
							    String status2Text = "";
							    if ("admin".equals(status2) || "emp".equals(status2)) {
							        status2Text = "����";
							    } else {
							        status2Text = "����";
							    }
							%>
						    <%= status2Text %>
							</td>
							<td><button>����</button></td>
							<td><button onclick="window.location.href='employeerevenue.html'">����</button></td>
							<td><button>��Ȱ��ȭ</button></td>
						</tr>
						
						<tr>
							<td><input type="checkbox" class="check"></td>
							
							<td>${Set3Num}</td>

							<td>${Set3Name}</td>

							<td>${Set3PhoneNumber}</td>

							<td>${Set3ID}</td>
							<td>${Set3PW}</td>
							<td>����(notyet)</td>
							<td>
							<%
							    String status3 = (String)request.getAttribute("Set3Status");
							
							    String status3Text = "";
							    if ("admin".equals(status3) || "emp".equals(status3)) {
							        status3Text = "����";
							    } else {
							        status3Text = "����";
							    }
							%>
						    <%= status3Text %>
							</td>
							<td><button>����</button></td>
							<td><button onclick="window.location.href='employeerevenue.html'">����</button></td>
							<td><button>��Ȱ��ȭ</button></td>
						</tr>
					
					</tbody>

				</table>
<!-- �ڵ����� db �����. �׷��� 203�����ο��� ��Ʈ ����(������� ��)�� ���� ��������� ��. -->
<!-- ���� 4�� ���(tiger1212)�� �̸��� ��ȭ��ȣ�� ���� �ٲ� ��µǴ� ������ ����. �̺κ� �����ؾ� ��. -->
<!-- �����͸� �������� ������Ű�� �۾� �ؾ���. -->
				<table id = "employTable">
				    <thead>
				        <tr>
				            <th>��ȣ</th>
				            <th>�̸�</th>
				            <th>��ȭ��ȣ</th>
				            <th>ID</th>
				            <th>PW</th>
				            <th>����</th>
				            <th>��ư1</th>
				            <th>��ư2</th>
				            <th>��ư3</th>
				        </tr>
				    </thead>
				    <tbody>
				        <%
				            int n =4 ; // n
				            for (int i = 1; i <= n; i++) {
				                String numKey = "Set" + i + "Num";
				                String nameKey = "Set" + i + "Name";
				                String phoneNumberKey = "Set" + i + "PhoneNumber";
				                String idKey = "Set" + i + "ID";
				                String pwKey = "Set" + i + "PW";
				                String statusKey = "Set" + i + "Status";
				                
				                String num = (String) request.getAttribute(numKey);
				                String name = (String) request.getAttribute(nameKey);
				                String phoneNumber = (String) request.getAttribute(phoneNumberKey);
				                String id = (String) request.getAttribute(idKey);
				                String pw = (String) request.getAttribute(pwKey);
				                String status = (String) request.getAttribute(statusKey);
				        %>
				        <tr>
				            <td><%= num %></td>
				            <td><%= name %></td>
				            <td><%= phoneNumber %></td>
				            <td><%= id %></td>
				            <td><%= pw %></td>
				            <td><%= status %></td>
				            <td><button>����</button></td>
				            <td><button onclick="window.location.href='employeerevenue.html'">����</button></td>
				            <td><button>��Ȱ��ȭ</button></td>
				        </tr>
				        <!-- ���� ���� ��Ȱ��ȭ �κ� ���� ���� ����. -->
				        <%
				            }
				        %>
				        
				    </tbody>
				       <tbody>
        <!-- ... ���� �� ���� ���� -->
        <tr>
            <td><input type="text" id="newNum"></td>
            <td><input type="text" id="newName"></td>
            <td><input type="text" id="newPhoneNumber"></td>
            <td><input type="text" id="newID"></td>
            <td><input type="text" id="newPW"></td>
            <td><input type="text" id="newStatus"></td>
            <td><button onclick="addRow()">����</button></td>
            <td><button onclick="addRow()">����</button></td>
            <td><button onclick="addRow()">��Ȱ��ȭ</button></td>
            <tr>
            <td><button onclick="addRow()">����</button></td>
        </tr>

    </tbody>
				    
				</table>

				
			</div>

		</div>

		<div class="footer">

			<p>BANKERS. fisabankers@gmail.com tel.010-XXXX-XXXX</p>

			<p>����Ư���� ������ �����źϷ� 434 (��ϵ�, ��� IT Tower 6��)</p>

		</div>
	</div>
</body>
</html>