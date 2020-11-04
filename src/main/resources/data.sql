insert into shopping_list(id, name, desc, status) VALUES
(1, 'lista tygodniowa', 'lista standarowych zakupów co tydzien', 'NEW'),
(2, 'obiadowa', 'lista zakupów na obiad', 'NEW'),
(3, 'lista różne', 'różne duperele do kupienia', 'FINISHED');

insert into item(id, name, shortdesc, price) VALUES
(1, 'makaron pełnoziarnisty', 'potrzebny bedzie do sosu z kurczakiem', 4.5),
(2, 'sos pomidorowy', 'najlepiej z dodatkiem ziół', 3.2),
(3, 'kasza gryczana', 'bedzie na wtorkowy obiad', 5);

insert into list_item(list_id, item_id) VALUES
(1,1),
(1,2),
(1,3),
(2,1),
(3,3),
(3,2);