SELECT
    ROUND(
        COUNT(DISTINCT a.player_id) * 1.0
        / (SELECT COUNT(DISTINCT player_id) FROM Activity),
        2
    ) AS fraction
FROM Activity a
WHERE EXISTS (
    SELECT 1
    FROM Activity b
    WHERE b.player_id = a.player_id
      AND b.event_date = DATE_ADD(
          (
              SELECT MIN(c.event_date)
              FROM Activity c
              WHERE c.player_id = a.player_id
          ),
          INTERVAL 1 DAY
      )
);