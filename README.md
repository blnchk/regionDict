Запросы:
1. GET /api/regions -- список всех регионов 
2. GET /api/regions/page/{page}?(limit,sortBy,order) -- постраничный список всех регионов
   1. page - номер страницы
   2. limit - кол-во строк на странице, def = 10
   3. sortBy - поле сортировки, def = id
   4. order - парметр сортировки(asc|desc), def = asc
3. GET /api/regions/{id} -- получение региона по его id
4. POST /api/regions -- добавление новго региона парметры(name, shortName)
5. PUT /api/regions/{id} -- изменение региона
6. DELETE /api/regions/{id} -- удаление региона