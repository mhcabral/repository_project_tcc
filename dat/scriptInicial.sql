--
-- PostgreSQL database dump
-- É necessário executar este script para a primeira execução do sistema
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

--
-- Data for Name: curso; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO curso VALUES (1, 'IE15', 'Sistemas de Informação', 120);
INSERT INTO curso VALUES (2, 'IE08', 'Ciência da Computação', 140);


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO usuario VALUES (1, true, '00000000000', 'admin@email.com', 'Administrador', '5f78b9345394376219b341fcf6cfdf23', 'ROOT');
INSERT INTO usuario VALUES (3, true, '762.274.455-76', 'brunabas22@gmail.com', 'Bruna Afonso Schramm', '5f78b9345394376219b341fcf6cfdf23', 'ALUNO');
INSERT INTO usuario VALUES (4, true, '712.681.328-70', 'thiagop.santos182@gmail.com', 'Thiago Santos', '5f78b9345394376219b341fcf6cfdf23', 'ALUNO');
INSERT INTO usuario VALUES (5, true, '214.851.893-30', 'janderson@email.com', 'Janderson Borges', '5f78b9345394376219b341fcf6cfdf23', 'ALUNO');
INSERT INTO usuario VALUES (6, true, '154.933.176-05', 'leticia@email.com', 'Letícia Santos', '5f78b9345394376219b341fcf6cfdf23', 'ALUNO');
INSERT INTO usuario VALUES (7, true, '539.256.143-89', 'andre@email.com', 'Andre Porto', '5f78b9345394376219b341fcf6cfdf23', 'ALUNO');
INSERT INTO usuario VALUES (8, true, '855.242.502-56', 'alti@email.com', 'Arilo Cláudio', '5f78b9345394376219b341fcf6cfdf23', 'PROFESSOR');
INSERT INTO usuario VALUES (10, true, '830.837.125-61', 'alti@mail.com', 'Altigran Soares da Silva', '5f78b9345394376219b341fcf6cfdf23', 'PROFESSOR');
INSERT INTO usuario VALUES (11, true, '334.434.538-99', 'leandro@email.com', 'Leandro Falcão', '5f78b9345394376219b341fcf6cfdf23', 'COORDENADOR');
INSERT INTO usuario VALUES (12, true, '862.781.131-80', 'fabiola@email.com', 'Fabíola Nakamura', '5f78b9345394376219b341fcf6cfdf23', 'COORDENADORACAD');
INSERT INTO usuario VALUES (14, true, '619.687.641-43', 'leo.felipe.si@gmail.com', 'Cristian Rossi', '5f78b9345394376219b341fcf6cfdf23', 'ALUNO');
INSERT INTO usuario VALUES (9, true, '861.086.489-88', 'eduardo@email.com', 'Eduardo Souto', '5f78b9345394376219b341fcf6cfdf23', 'COORDENADOR');
INSERT INTO usuario VALUES (13, true, '212.869.989-48', 'eleinai@email.com', 'Elienai Nogueira', '5f78b9345394376219b341fcf6cfdf23', 'SECRETARIA');
INSERT INTO usuario VALUES (2, true, '013.575.952-80', 'leo.felipe.si@gmail.co', 'Leonardo Felipe da Silva Freitas', '5f78b9345394376219b341fcf6cfdf23', 'ALUNO');


--
-- Data for Name: aluno; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO aluno VALUES (1, '2010-02-08', '21000001', 1, 2);
INSERT INTO aluno VALUES (2, '2010-02-08', '21000002', 1, 3);
INSERT INTO aluno VALUES (3, '2010-02-08', '21000003', 1, 4);
INSERT INTO aluno VALUES (4, '2010-02-08', '21000004', 1, 5);
INSERT INTO aluno VALUES (5, '2009-02-09', '21000005', 2, 6);
INSERT INTO aluno VALUES (6, '2009-02-09', '21000006', 2, 7);
INSERT INTO aluno VALUES (7, '2007-02-12', '21000007', 2, 14);


--
-- Name: aluno_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('aluno_id_seq', 7, true);


--
-- Data for Name: aprovacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: aprovacao_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('aprovacao_id_seq', 1, false);


--
-- Data for Name: grupo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO grupo VALUES (1, 'GP01', 'Atividades de Promoção da Cidadania');
INSERT INTO grupo VALUES (2, 'GP02', 'Atividades de Intervenção Organizacional');
INSERT INTO grupo VALUES (3, 'GP03', 'Participação em Eventos Técnico-Científicos');
INSERT INTO grupo VALUES (4, 'GP04', 'Produção Técnico-Científica');
INSERT INTO grupo VALUES (5, 'GP05', 'Iniciação Científica');


--
-- Data for Name: atividade; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO atividade VALUES (1, 'EXTENSAO', '1.0', 'Relatório do trabalho realizado pelo aluno e carga horária declarada pelo professor supervisor da atividade.', 'Engajamento em trabalho comunitário em centros sociais, asilos, escolas, comunidades, hospitais, entidades filantrópicas, entre outras.', 1);
INSERT INTO atividade VALUES (2, 'EXTENSAO', '2.1', 'Relatório de atividades do projeto de consultoria, com carga horária declarada pelo supervisor.', 'Participação em projetos de consultoria.', 2);
INSERT INTO atividade VALUES (3, 'EXTENSAO', '2.2', 'Relatório da visita, com a carga horária declarada pelo professor.', 'Visita técnica às organizações.', 2);
INSERT INTO atividade VALUES (4, 'ENSINO', '3.1', 'Certificado de participação.', 'Participação em eventos da SBC.', 3);
INSERT INTO atividade VALUES (5, 'ENSINO', '3.3', 'Certificado de participação.', 'Participação em congressos, seminários, simpósios, conferências, fóruns, workshops, semana de curso, etc.', 3);
INSERT INTO atividade VALUES (6, 'PESQUISA', '4.1', 'Artigo impresso, declaração de aceite e certificado de apresentação do artigo no evento.', 'Autor ou co-autor de artigo científico completo publicado em anais.', 4);
INSERT INTO atividade VALUES (7, 'PESQUISA', '4.2', 'Apresentação de cópia da capa e índice do livro.', 'Autor ou co-autor de capítulo de livro.', 4);
INSERT INTO atividade VALUES (8, 'PESQUISA', '5.1', 'Relatório de atividades do aluno, acompanhado da avaliação do professor coordenador do projeto.', 'Participação em projetos de pesquisa aprovados em outros programas.', 5);


--
-- Name: atividade_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--



--
-- Data for Name: professor; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO professor VALUES (1, 8);
INSERT INTO professor VALUES (2, 9);
INSERT INTO professor VALUES (3, 10);
INSERT INTO professor VALUES (4, 11);
INSERT INTO professor VALUES (5, 12);


--
-- Data for Name: coordenadoracademico; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO coordenadoracademico VALUES (1, 5);


--
-- Data for Name: coordenadoracademico_curso; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO coordenadoracademico_curso VALUES (1, 1);
INSERT INTO coordenadoracademico_curso VALUES (1, 2);


--
-- Name: coordenadoracademico_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--



--
-- Data for Name: coordenadorcurso; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO coordenadorcurso VALUES (1, 2, 4);
INSERT INTO coordenadorcurso VALUES (2, 1, 2);


--
-- Name: coordenadorcurso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--



--
-- Name: curso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--



--
-- Data for Name: disciplina; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO disciplina VALUES (1, 'IEC112', 'PRÁTICA DE ANÁLISE E PROJETO DE SISTEMAS');
INSERT INTO disciplina VALUES (2, 'IEC115', 'PRÁTICA EM BANCO DE DADOS');
INSERT INTO disciplina VALUES (3, 'IEC483', 'INTERAÇÃO HUMANO COMPUTADOR');
INSERT INTO disciplina VALUES (4, 'IEC485', 'QUALIDADE DE SOFTWARE');
INSERT INTO disciplina VALUES (5, 'IEC921', 'GERENCIA DE PROJETOS');


--
-- Name: disciplina_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--



--
-- Data for Name: periodoletivo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO periodoletivo VALUES (1, '2013/1', '2013-06-28', '2013-04-26', '2013-04-26', '2013-04-22', '2013-04-22', '2013-04-22');

INSERT INTO periodocadmon VALUES (1, '2013-04-19', '2013-04-16', 1);


--
-- Data for Name: periodoinsmon; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO periodoinsmon VALUES (1, '2013-04-26', '2013-04-22');
--
-- Data for Name: relatoriofinal; Type: TABLE DATA; Schema: public; Owner: postgres
--INSERT INTO regra VALUES (1, 5, 1, 1);
INSERT INTO regra VALUES (2, 3, 2, 1);
INSERT INTO regra VALUES (3, 3, 3, 1);
INSERT INTO regra VALUES (4, 5, 4, 1);
INSERT INTO regra VALUES (5, 5, 5, 1);
INSERT INTO regra VALUES (6, 15, 6, 1);
INSERT INTO regra VALUES (7, 15, 7, 1);
INSERT INTO regra VALUES (8, 15, 8, 1);

--

INSERT INTO regragrupo VALUES (1, 30, 1, 1);
INSERT INTO regragrupo VALUES (2, 30, 1, 2);
INSERT INTO regragrupo VALUES (3, 50, 1, 3);
INSERT INTO regragrupo VALUES (4, 80, 1, 4);
INSERT INTO regragrupo VALUES (5, 80, 1, 5);


--
-- Data for Name: secretaria; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO secretaria VALUES (1, 13);


--
-- Data for Name: secretaria_curso; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO secretaria_curso VALUES (1, 2);
INSERT INTO secretaria_curso VALUES (1, 1);



--
-- Data for Name: estagio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: estagio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--



--
-- Data for Name: monitoria; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: inscricaomonitoria; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: frequencia; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: frequencia_estagio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--



--
-- Name: frequencia_estagio_mensal_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--



--
-- Name: frequencia_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--



--
-- Name: frequencia_mensal_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--



--
-- Data for Name: frequenciaestagio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: frequenciamensal; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: frequenciamensal_frequencia; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: frequenciamensalestagio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: frequenciamensalestagio_aprovacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: frequenciamensalestagio_frequenciaestagio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: grupo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('grupo_id_seq', 5, true);
SELECT pg_catalog.setval('frequencia_id_seq', 1, false);
SELECT pg_catalog.setval('frequencia_mensal_id_seq', 1, false);
SELECT pg_catalog.setval('frequencia_estagio_mensal_id_seq', 1, false);
SELECT pg_catalog.setval('frequencia_estagio_id_seq', 1, false);
SELECT pg_catalog.setval('estagio_id_seq', 1, false);
SELECT pg_catalog.setval('disciplina_id_seq', 5, true);
SELECT pg_catalog.setval('coordenadorcurso_id_seq', 2, true);
SELECT pg_catalog.setval('coordenadoracademico_id_seq', 1, true);
SELECT pg_catalog.setval('atividade_id_seq', 8, true);
SELECT pg_catalog.setval('curso_id_seq', 2, true);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 1, false);


--
-- Name: horario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('horario_id_seq', 1, false);


--
-- Data for Name: horarioturma; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: inscricao_monitoria_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('inscricao_monitoria_id_seq', 1, false);


--
-- Data for Name: inscricaomonitoria_frequencia; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: monitoria_horarioturma; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: mudanca; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: notificacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: periodo_cad_mon_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('periodo_cad_mon_id_seq', 1, true);


--
-- Name: periodo_ins_mon_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('periodo_ins_mon_id_seq', 1, true);


--
-- Name: periodo_letivo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('periodo_letivo_id_seq', 1, true);


--
-- Data for Name: periodocadmon; Type: TABLE DATA; Schema: public; Owner: postgres
--




--
-- Data for Name: professor_curso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: professor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('professor_id_seq', 5, true);


--
-- Data for Name: regra; Type: TABLE DATA; Schema: public; Owner: postgres
--


--
-- Name: regra_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('regra_id_seq', 8, true);


--
-- Data for Name: regragrupo; Type: TABLE DATA; Schema: public; Owner: postgres



--
-- Name: regragrupo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('regragrupo_id_seq', 5, true);


--
-- Name: relatorio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('relatorio_id_seq', 1, false);


--
-- Data for Name: relatoriofinal_aprovacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: secretaria_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('secretaria_id_seq', 1, true);


--
-- Data for Name: solicitacao; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: solicitacao_anexos; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: solicitacao_mudanca; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuario_id_seq', 14, true);


--
-- PostgreSQL database dump complete
--

