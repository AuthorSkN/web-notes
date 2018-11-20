<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src=" https://code.jquery.com/jquery-3.3.1.js "></script>
</head>

<body>
<h2>Hello, Web Notes!</h2>
<button onclick="arr();">Кнопка</button>

<script lang="javascript">
    function arr() {
        $.get("/webnotes-web/hello", function(){
            alert("Yeesss!");
        });
    }
</script>
</body>
</html>
