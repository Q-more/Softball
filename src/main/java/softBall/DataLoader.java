package softBall;

import java.util.function.Function;;
import lombok.Data;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import softBall.model.Batting;
import softBall.model.CSVDataClass;
import softBall.model.Fielding;
import softBall.model.Pitching;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lucija Raženj
 * @version 1.0
 */
@Data
public class DataLoader extends SimpleFileVisitor<Path> {
    
    private String[] batterHeaders;
    private Map<String, Batting> battingMap;
    
    private String[] pitchingHeaders;
    private Map<String, Pitching> pitchingMap;
    
    private String[] fieldingHeaders;
    private Map<String, Fielding> fieldingMap;

    public DataLoader() {
        battingMap = new HashMap<>();
        pitchingMap = new HashMap<>();
        fieldingMap = new HashMap<>();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.toString().toLowerCase().contains("pitching.csv")) {
            fillPitching(file);
        }

        if (file.toString().toLowerCase().contains("batting.csv")) {
            fillBatters(file);
        }

        if (file.toString().toLowerCase().contains("fielding.csv")) {
            fillFielding(file);
        }

        return FileVisitResult.CONTINUE;
    }
    
    private <T extends CSVDataClass> T merge(T left, T right, CSVDataClass.CSVDataBuilder<T> builder) {
        Map<String, Double> parameterMap = EntryStream.of(left.getParameters())
            .mapToValue((key, oldValue) -> oldValue + right.getParameter(key))
            .toMap();
        return builder.build(left.getNum(), left.getName(), parameterMap);
    }
    
    private <T extends CSVDataClass> void fill(Map<String, T> dataMap, List<T> values, CSVDataClass.CSVDataBuilder<T> builder) {
        StreamEx.of(values)
            .mapToEntry(
                CSVDataClass::getName,
                Function.identity())
            .toMap((left, right) -> merge(left, right, builder))
            .forEach((name, data) -> {
                dataMap.merge(name, data, (left, right) -> merge(left,right, builder));
            });
    }

    private void fillPitching(Path path) {
        List<Pitching> pitchings = Pitching.fromCsv(path);
        fill(pitchingMap, pitchings, Pitching::new);
        if (pitchingHeaders == null) {
            pitchingHeaders = pitchings.get(0).getParameterNames();
        }
//
//
//        List<Pitching> pitchings = Pitching.fromCSV(path);stringTMap
//
//        pitchings.forEach((pitching -> {
//            pitchingMap.merge(pitching.getName(), pitching, (oldV, newV) -> {
//
//                double g = oldV.getG() + newV.getG();
//                double w = oldV.getW() + newV.getW();
//                double l = oldV.getL() + newV.getL();
//                double sv = oldV.getSv() + newV.getSv();
//                double ip = oldV.getIp() + newV.getIp();
//                double bf = oldV.getBf() + newV.getBf();
//                double ball = oldV.getBall() + newV.getBall();
//                double str = oldV.getStr() + newV.getStr();
//                double pit = oldV.getPit() + newV.getPit();
//                double r = oldV.getR() + newV.getR();
//                double ra = r + ip;
//                double er = oldV.getEr() + newV.getEr();
//                double k = oldV.getK() + newV.getK();
//                double kc = oldV.getKc() + newV.getKc();
//                double ks = oldV.getKs() + newV.getKs();
//                double h = oldV.getH() + newV.getH();
//                double bb = oldV.getBb() + newV.getBb();
//                double ibb = oldV.getIbb() + newV.getIbb();
//                double kbb = 0.0;
//                double kgi = 0.0;
//                double bbgi = 0.0;
//                double hgi = 0.0;
//                double era = 0.0;
//                double ers9 = 0.0;
//                double whip = 0.0;
//
//                if (bb != 0.0) {
//                    kbb = k / bb;
//                }
//                if (ip != 0.0) {
//                    era = (7 * er) / ip;
//                    ers9 = er / ip;
//                    kgi = k / ip;
//                    bbgi = bb / ip;
//                    hgi = h / ip;
//                    whip = (bb + ibb + h) / ip;
//                }
//
//                double hb = oldV.getHb() + newV.getHb();
//                double bk = oldV.getBk() + newV.getBk();
//                double wp = oldV.getWp() + newV.getWp();
//                double hr = oldV.getHr() + newV.getHr();
//                double obp = 0.0;//to treba izbacit
//                double baa = h / (bf + bb + hb);
//                double g0 = oldV.getG0() + newV.getG0();
//                double a0 = oldV.getA0() + newV.getA0();
//                double fps = oldV.getFps() + newV.getFps();
//                double fbp = oldV.getFbp() + newV.getFbp();
//                double fpsPercentage = 0.0;
//                if (pit != 0.0) {
//                    fpsPercentage = fps / pit;
//                }
//                return new Pitching(oldV.getJerseyNumber(), oldV.getName(), g, w, l, sv, ip, bf, ball, str, pit, r, ra, er, era, ers9, k, kc, ks, h, bb, ibb, kbb, kgi, bbgi, hgi, hb, bk, wp, hr, whip, obp, baa, g0, a0, fps, fbp, fpsPercentage);
//            });
//        }));
    }

    private void fillFielding(Path path) {
        List<Fielding> fieldings = Fielding.fromCsv(path);
        fill(fieldingMap, fieldings, Fielding::new);
        if (fieldingHeaders == null) {
            fieldingHeaders = fieldings.get(0).getParameterNames();
        }
        //
//
//        List<Fielding> fieldings = Fielding.fromFile(path);
//
//        fieldings.forEach((fielding -> {
//            fieldingMap.merge(fielding.getName(), fielding, (oldV, newV) -> {
//                double g = oldV.getG() + newV.getG();
//                double et = oldV.getEt() + newV.getEt();
//                double ef = oldV.getEf() + newV.getEf();
//                double err = et + ef;
//                double po = oldV.getPo() + newV.getPo();
//                double a = oldV.getA() + newV.getA();
//                double sba = oldV.getSba() + newV.getSba();
//                double cs = oldV.getCs() + newV.getCs();
//                double dp = oldV.getDp() + newV.getDp();
//                double tp = oldV.getTp() + newV.getTp();
//                double pb = oldV.getPb() + newV.getPb();
//                double pkf = oldV.getPkf() + newV.getPkf();
//                double pk = oldV.getPk() + newV.getPk();
//                double fp = 0.0;
//                if (err + a + po != 0.0) {
//                    fp = (po + a) / (err + a + po);
//                }
//
//                return new Fielding(oldV.getJerseyNumber(), oldV.getName(), g,
//                        et, ef, err, po, a, sba, cs, dp, tp, pb, pkf, pk, fp);
//            });
//        }));
    }

    private void fillBatters(Path path) {
        List<Batting> battings = Batting.fromCsv(path);
        fill(battingMap, battings, Batting::new);
        if (batterHeaders == null) {
            batterHeaders = battings.get(0).getParameterNames();
        }
//        List<Batting> battings = Batting.fromCSV(path);
//
//        battings.forEach((batting) -> {
//            battingMap.merge(batting.getName(), batting, (oldV, newV) -> {
//                Double G = oldV.getG() + newV.getG();
//                Double PA = oldV.getPA() + newV.getPA();
//                Double AB = oldV.getAB() + newV.getAB();
//                Double R = oldV.getR() + newV.getR();
//                Double H = oldV.getH() + newV.getH();
//                Double B = oldV.getB() + newV.getB();
//                Double B1 = oldV.getB1() + newV.getB1();
//                Double B2 = oldV.getB2() + newV.getB2();
//                Double B3 = oldV.getB3() + newV.getB3();
//                Double HR = oldV.getHR() + newV.getHR();
//                Double RBI = oldV.getRBI() + newV.getRBI();
//                Double SO = oldV.getSO() + newV.getSO();
//                Double AVG = 0.0;
//                Double SLG = 0.0;//krivo
//                Double CT_per = 0.0;
//                Double CT2_per = 0.0;
//                if (AB != 0.0) {
//                    AVG = H / AB;
//                    SLG = (B1 + B2 * 2 + B3 * 3 + HR * 4) / AB;
//                    CT_per = (AB - SO) / AB;
//                }
//                Double BB = oldV.getBB() + newV.getBB();
//                Double Kc = oldV.getKc() + newV.getKc();
//                Double Ks = oldV.getKs() + newV.getKs();
//                Double HBP = oldV.getHBP() + newV.getHBP();
//                Double SB = oldV.getSB() + newV.getSB();
//                Double CS = oldV.getCS() + newV.getCS();
//                Double SCB = oldV.getSCB() + newV.getSCB();
//                Double SF = oldV.getSF() + newV.getSF();
//                Double SAC = oldV.getSAC() + newV.getSAC();
//                Double RPA = 0.0;
//                if (PA != 0.0) {
//                    RPA = (R + RBI) / PA;
//                    CT2_per = (AB - SO) / PA;
//                }
//                Double ROE = oldV.getROE() + newV.getROE();
//                Double OBP = 0.0;//krivo
//                Double OBPE = 0.0;
//                if ((AB + BB + SF) != 0.0) {
//
//                    OBP = (H + BB + HBP + ROE) / (HBP + AB + BB + SF);
//                    OBPE = (H + BB + HBP) / (AB + BB + SF + HBP);
//                }
//                Double OPS = SLG + OBP;
//                Double GPA = (1.8 * SLG + OBP) / 4;
//                Double FC = oldV.getFC() + newV.getFC();
//                Double CI = oldV.getCI() + newV.getCI();
//                Double GDP = oldV.getGDP() + newV.getGDP();
//                Double GTP = oldV.getGTP() + newV.getGTP();
//                Double AB_RSP = oldV.getAB_RSP() + newV.getAB_RSP();
//                Double H_RSP = oldV.getH_RSP() + newV.getH_RSP();
//                Double BA_RSP = oldV.getBA_RSP() + newV.getBA_RSP();
//
//                return new Batting(oldV.getJerseyNumber(), oldV.getName(), G, PA, AB, R, H, B, B1, B2, B3, HR, RBI, AVG, BB, Kc, Ks, SO, HBP, SB, CS, SCB, SF, SAC, RPA, OBP, OBPE, OPS, SLG, GPA, CT_per, CT2_per, ROE, FC, CI, GDP, GTP, AB_RSP, H_RSP, BA_RSP);
//            });
//        });
    }

}
