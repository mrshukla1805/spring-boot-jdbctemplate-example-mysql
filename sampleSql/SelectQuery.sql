
Select *
from key
where is_blocked = false
and created_at > now() - interval 5 minute
and keep_alive_at > now() - interval 5 minute
order by created_at
LIMIT 1