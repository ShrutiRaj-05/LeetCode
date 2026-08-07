# Write your MySQL query statement below
SELECT 
    request_at AS Day,
    ROUND(
        AVG(status != 'completed'), 
        2
    ) AS `Cancellation Rate`
FROM Trips
WHERE request_at BETWEEN '2013-10-01' AND '2013-10-03'
  AND client_id NOT IN (SELECT users_id FROM Users WHERE banned = 'Yes')
  AND driver_id NOT IN (SELECT users_id FROM Users WHERE banned = 'Yes')
GROUP BY request_at;