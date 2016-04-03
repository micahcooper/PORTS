<html>
	<head>
		<title>Login</title>
	</head>
	<body>
		<h1>Please Log In</h1>
		<div>
			<div>
				${error!}
			</div>
			<form action="Login" method="POST">
				<input type="text" name="username"/><br/>
				<input type="password" name="password"/><br/>
				<input type="submit" value="Login"/>
			</form>
		</div>
	</body>
</html>