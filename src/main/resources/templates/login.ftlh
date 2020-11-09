<!DOCTYPE html>
<html lang="de" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Contact-List</title>
    <link rel="stylesheet" href="stylesheet.css">
</head>
<body>

<header>
    <h1>Contact List</h1>
</header>

<main>

    <form action="/login" method="post">
        <#if error>
            <p class="error">Die Anmeldung ist fehlgeschlagen.</p>
        </#if>
        <#if logout>
            <p class="info">Sie wurden abgemeldet.</p>
        </#if>
        <label for="username">Username:</label>
        <input id="username" name="username">
        <label for="password">Passwort:</label>
        <input id="password" type="password" name="password">
        <button type="submit">Login</button>
    </form>

</main>

</body>
</html>
