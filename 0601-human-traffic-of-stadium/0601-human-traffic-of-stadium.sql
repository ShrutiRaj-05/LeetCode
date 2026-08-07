# Write your MySQL query statement below
WITH FilteredStadium AS (
    SELECT 
        id,
        visit_date,
        people,
        LEAD(id, 1) OVER (ORDER BY id) AS next_id1,
        LEAD(id, 2) OVER (ORDER BY id) AS next_id2,
        LAG(id, 1) OVER (ORDER BY id) AS prev_id1,
        LAG(id, 2) OVER (ORDER BY id) AS prev_id2
    FROM Stadium
    WHERE people >= 100
)
SELECT id, visit_date, people
FROM FilteredStadium
WHERE 
    -- Current row is the 1st of three consecutive
    (next_id1 = id + 1 AND next_id2 = id + 2)
    -- Current row is the 2nd of three consecutive
    OR (prev_id1 = id - 1 AND next_id1 = id + 1)
    -- Current row is the 3rd of three consecutive
    OR (prev_id1 = id - 1 AND prev_id2 = id - 2)
ORDER BY visit_date ASC;