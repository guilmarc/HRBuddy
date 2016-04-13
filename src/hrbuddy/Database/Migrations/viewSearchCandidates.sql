CREATE VIEW viewSearchCandidates AS
SELECT *, firstname || " " || lastname || " " ||  address || " " || city || " " || cell_phone || " " || home_phone || " " || email   as SearchField
FROM candidates;