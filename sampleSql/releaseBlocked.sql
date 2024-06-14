Update keys
Set is_blocked = false
blocked_at = NULL
where is_blocked = TRUE
AND blocked_at <= now() - interval 60 second;
