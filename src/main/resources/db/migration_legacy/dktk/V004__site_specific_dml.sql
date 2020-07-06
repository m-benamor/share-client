WITH
    teilerIds AS (SELECT
                    'TeilerB'  AS Berlin,
                    'ttlrb1'  AS BerlinTest,
                    'TeilerDD' AS Dresden,
                    'ttlrdd1' AS DresdenTest,
                    'TeilerE'  AS Essen,
                    'ttlre1'  AS EssenTest,
                    'TeilerF'  AS Frankfurt,
                    'ttlef1'  AS FrankfurtTest,
                    'TeilerFR'  AS Freiburg,
                    'ttlrfr1'  AS FreiburgTest,
                    'TeilerHD'  AS Heidelberg,
                    'ttlrhd1'  AS HeidelbergTest,
                    'TeilerMZ'  AS Mainz,
                    'ttlrmz1'  AS MainzTest,
                    'TeilerML'  AS MuenchenLMU,
                    'ttlrmu1'  AS MuenchenLMUTest,
                    'TeilerMT'  AS MuenchenTUM,
                    'ttlrmu2'  AS MuenchenTUMTest,
                    'TeilerTÜ'  AS Tuebingen,
                    'ttlrtu1'  AS TuebingenTest,
                    'ttlkairos'  AS Kairos,
                    'ttlrhd3'  AS Teststandort),

    instanceIds AS (SELECT
                      'berlin'  AS Berlin,
                      'berlin'  AS BerlinTest,
                      'dresden' AS Dresden,
                      'dresden' AS DresdenTest,
                      'essen'  AS Essen,
                      'essen'  AS EssenTest,
                      'frankfurt'  AS Frankfurt,
                      'frankfurt'  AS FrankfurtTest,
                      'freiburg'  AS Freiburg,
                      'freiburg'  AS FreiburgTest,
                      'heidelberg'  AS Heidelberg,
                      'heidelberg'  AS HeidelbergTest,
                      'mainz'  AS Mainz,
                      'mainz'  AS MainzTest,
                      'lmu'  AS MuenchenLMU,
                      'lmu'  AS MuenchenLMUTest,
                      'tum'  AS MuenchenTUM,
                      'tum'  AS MuenchenTUMTest,
                      'tübingen'  AS Tuebingen,
                      'teststandort'  AS Teststandort,
                      'kairos'  AS Kairos,
                      'tübingen'  AS TuebingenTest)

UPDATE samply.configuration
SET setting = ('/dktk/sites/' ||
               (SELECT instanceIds.INSTANCEIDPLACEHOLDER
                       FROM instanceIds)::TEXT ||
               '/teiler/' ||
               (SELECT teilerIds.INSTANCEIDPLACEHOLDER
                       FROM teilerIds)::TEXT ||
               '/')
WHERE name = 'CENTRAL_MDS_DATABASE_PATH';

UPDATE samply.configuration SET setting = 'INSTANCEIDPLACEHOLDER' where name = 'ID_MANAGER_INSTANCE_ID';
