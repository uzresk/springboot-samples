SELECT /*%expand*/*
FROM member
WHERE 
/*%if @isNotEmpty(condition.userId) */
   user_id = /* condition.userId */'uzresk' 
/*%end*/
/*%if @isNotEmpty(condition.name) */
   AND name LIKE /* @prefix(condition.name) */'uzresk'
/*%end*/
ORDER BY user_id