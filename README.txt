REST-запросы: 

примеры POST-запросов и ответов:

В первых двух запросах тип операции указывается в ссылке, в третьем тип операции передается в теле ("operation_name" : "get_median")
В четвертом используется header (application/xml) для получения ответа в xml-формате

1.
curl --location --request POST 'http://localhost:8080/test/operation/get_max_value' \
--header 'accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "file_path" : "files/10m.txt"
}'
responce : 
{
    "max_value": 49999978
}

2.
curl --location --request POST 'http://localhost:8080/test/operation/get_average_value' \
--header 'accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "file_path" : "files/10m.txt"
}'
responce:
{
    "average_value": 7364.418442641844
}

3.
curl --location --request POST 'http://localhost:8080/test' \
--header 'accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "operation_name" : "get_median",
    "file_path" : "files/10m.txt"
}'
responce:
{
    "median": 25216
}

4.
curl --location --request POST 'http://localhost:8080/test' \
--header 'accept: application/xml' \
--header 'Content-Type: application/json' \
--data-raw '{
    "operation_name" : "get_median",
    "file_path" : "files/10m.txt"
}'
responce:
<Map1>
    <median>25216</median>
</Map1>



типы операций совпадают с названиями методов в OperationService:
get_max_value
get_min_value
get_average_value
get_median
get_longest_subsequence_inc
get_longest_subsequence_dec




все найденные числа и последовательности:
max_value : 49999978
min_value : -49999996
average_value : 7364.418442641844
median : 25216
longest increasing subsequence :  -48190694, -47725447, -43038241, -20190291, -17190728, -6172572, 8475960, 25205909, 48332507, 48676185
longest decreasing subsequence :  47689379, 42381213, 30043880, 12102356, -4774057, -5157723, -11217378, -23005285, -23016933, -39209115, -49148762

все необходимые требования выполнены, из дополнительных выполнены:
* возвращение данных в json или xml (4 запрос)
* настройка swagger
* кеширование с помощью Spring Cache (в данном случае кеширование проводилось не по хеш-функции файла, а по названию файла и операции, так как на вычисление хеш-функции файла уходит много времени, смысла в этом немного.
* отправку файлов в бинарном виде не удалось, возникала ошибка с Unsupported media type.



