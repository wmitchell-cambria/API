-- MAINFRAME:
-- FIRST:

delete from cwsint.REFERL_T where IDENTIFIER like '%5Y3vKVs0X5%';
delete from cwsint.ALLGTN_T where identifier = '4MzcZXA0X5';
delete from cwsint.CRSS_RPT where FKREFERL_T = '5Y3vKVs0X5' and third_id = 'Rn9BEYe0X5';
delete from cwsint.REFR_CLT where FKREFERL_T = '5Y3vKVs0X5' and fkclient_t = 'Ov6RE3Q0X5';
delete from cwsint.reptr_t where  FKREFERL_T = '5Y3vKVs0X5';

commit;


select * from cwsint.REFERL_T where IDENTIFIER like '5Y3vKVs0X5';
select * from cwsint.ALLGTN_T where identifier = '4MzcZXA0X5';
select * from cwsint.CRSS_RPT where FKREFERL_T = '5Y3vKVs0X5' and third_id = 'Rn9BEYe0X5';
select * from cwsint.REFR_CLT where FKREFERL_T = '5Y3vKVs0X5' and fkclient_t = 'Ov6RE3Q0X5';
select * from cwsint.REPTR_T where FKREFERL_T = '5Y3vKVs0X5';



-- SECOND:

delete from cwsint.REFERL_T where IDENTIFIER like '%MrWIdIi0X5%';
delete from cwsint.ALLGTN_T where identifier = 'NAGxc4y0X5';
delete from cwsint.CRSS_RPT where FKREFERL_T = 'MrWIdIi0X5' and third_id = 'NT1carE0X5';
delete from cwsint.REFR_CLT where FKREFERL_T = 'MrWIdIi0X5' and fkclient_t = 'Ov6RE3Q0X5';
delete from cwsint.reptr_t where  FKREFERL_T = 'MrWIdIi0X5';

commit;


select * from cwsint.REFERL_T where IDENTIFIER like 'MrWIdIi0X5';
select * from cwsint.ALLGTN_T where identifier = 'NAGxc4y0X5';
select * from cwsint.CRSS_RPT where FKREFERL_T = 'MrWIdIi0X5' and third_id = 'NT1carE0X5';
select * from cwsint.REFR_CLT where FKREFERL_T = 'MrWIdIi0X5' and fkclient_t = 'Ov6RE3Q0X5';
select * from cwsint.REPTR_T where FKREFERL_T = 'MrWIdIi0X5';




