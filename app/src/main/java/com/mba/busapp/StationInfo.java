package com.mba.busapp;

import android.util.Log;

import com.naver.maps.geometry.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 정류장 정보, 경로 관리
 */
public class StationInfo {
    private static StationInfo instance;

    // 정류장 정보
    private final static HashMap<String, LatLng> stationInfo = new HashMap<String, LatLng>() {{
        put("명현관", new LatLng(37.2233933, 127.1813636));
        put("함박관", new LatLng(37.219509212602546, 127.1829915220452));
        put("명지대 정문", new LatLng(37.2241558, 127.1878371));
        put("명지대", new LatLng(37.224283500000006, 127.18728609999998));
        put("이마트·상공회의소", new LatLng(37.230680400000004, 127.1882456));
        put("진입로", new LatLng(37.23399210000001,  127.18882909999999));
        put("명지대역", new LatLng(37.238513300000015, 127.18960559999998));
        put("진입로(명지대방향)", new LatLng(37.233999900000015, 127.18861349999999));
        put("이마트·상공회의소(명지대방향)", new LatLng(37.23036920601031 , 127.18799722805205));
        put("명진당", new LatLng(37.22218358841614, 127.18895343450612));
        put("제3공학관", new LatLng(37.219509212602546, 127.1829915220452));
        put("동부경찰서", new LatLng(37.23475516860965 , 127.19817660622552));
        put("용인시장", new LatLng(37.235430174474516, 127.20667763142193));
        put("중앙공영주차장", new LatLng(37.23391585619981 , 127.20892718244508));
        put("제1공학관", new LatLng(37.22271140883418, 127.18678412115244));
        put("기흥역", new LatLng(37.2746753, 127.1157522));
    }};

    // HashMap, [출발지, 도착지, 경로]
    private final static HashMap<String, HashMap<String, ArrayList<LatLng>>> routeInfo = new HashMap<>();

    private StationInfo() {
        // Init double key hashmap
        for(String station: stationInfo.keySet()) {
            routeInfo.put(station, new HashMap<>());
        }

        // Init route Arraylist
        ArrayList<LatLng> routes;

        // 명지대 ~ 상공회의소 경로
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2242590,127.1872767));
        routes.add(new LatLng(37.2242341,127.1873974));
        routes.add(new LatLng(37.2242208,127.1874831));
        routes.add(new LatLng(37.2242236,127.1875428));
        routes.add(new LatLng(37.2242653,127.1876294));
        routes.add(new LatLng(37.2243034,127.1877093));
        routes.add(new LatLng(37.2243116,127.1877532));
        routes.add(new LatLng(37.2242795,127.1878977));
        routes.add(new LatLng(37.2246345,127.1878128));
        routes.add(new LatLng(37.2246930,127.1878024));
        routes.add(new LatLng(37.2246984,127.1878013));
        routes.add(new LatLng(37.2247813,127.1877919));
        routes.add(new LatLng(37.2248237,127.1877917));
        routes.add(new LatLng(37.2248940,127.1877903));
        routes.add(new LatLng(37.2249382,127.1877901));
        routes.add(new LatLng(37.2249400,127.1878014));
        routes.add(new LatLng(37.2249446,127.1878217));
        routes.add(new LatLng(37.2249545,127.1878385));
        routes.add(new LatLng(37.2249681,127.1878497));
        routes.add(new LatLng(37.2249825,127.1878564));
        routes.add(new LatLng(37.2250006,127.1878564));
        routes.add(new LatLng(37.2250141,127.1878507));
        routes.add(new LatLng(37.2250267,127.1878405));
        routes.add(new LatLng(37.2250383,127.1878213));
        routes.add(new LatLng(37.2250401,127.1878100));
        routes.add(new LatLng(37.2251195,127.1878457));
        routes.add(new LatLng(37.2251547,127.1878625));
        routes.add(new LatLng(37.2251556,127.1878625));
        routes.add(new LatLng(37.2252513,127.1879083));
        routes.add(new LatLng(37.2252666,127.1879161));
        routes.add(new LatLng(37.2253704,127.1879642));
        routes.add(new LatLng(37.2255003,127.1879986));
        routes.add(new LatLng(37.2255995,127.1879959));
        routes.add(new LatLng(37.2257040,127.1879899));
        routes.add(new LatLng(37.2257184,127.1879876));
        routes.add(new LatLng(37.2258247,127.1879623));
        routes.add(new LatLng(37.2262246,127.1878367));
        routes.add(new LatLng(37.2263498,127.1877990));
        routes.add(new LatLng(37.2266831,127.1877243));
        routes.add(new LatLng(37.2271750,127.1875994));
        routes.add(new LatLng(37.2275085,127.1875913));
        routes.add(new LatLng(37.2277077,127.1875871));
        routes.add(new LatLng(37.2277158,127.1875882));
        routes.add(new LatLng(37.2279250,127.1876121));
        routes.add(new LatLng(37.2279647,127.1876198));
        routes.add(new LatLng(37.2281640,127.1876573));
        routes.add(new LatLng(37.2283831,127.1876993));
        routes.add(new LatLng(37.2284102,127.1877048));
        routes.add(new LatLng(37.2290126,127.1878173));
        routes.add(new LatLng(37.2290658,127.1878272));
        routes.add(new LatLng(37.2291984,127.1878548));
        routes.add(new LatLng(37.2296124,127.1879366));
        routes.add(new LatLng(37.2304322,127.1880955));
        routes.add(new LatLng(37.2305674,127.1881254));
        routes.add(new LatLng(37.2306793,127.1881464));
        routes.add(new LatLng(37.2306919,127.1881486));
        routeInfo.get("명지대").put("이마트·상공회의소", routes);

        // 상공회의소 ~ 진입로
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2306919,127.1881486));
        routes.add(new LatLng(37.2307884,127.1881684));
        routes.add(new LatLng(37.2310247,127.1882114));
        routes.add(new LatLng(37.2311203,127.1882313));
        routes.add(new LatLng(37.2311906,127.1882457));
        routes.add(new LatLng(37.2312628,127.1882612));
        routes.add(new LatLng(37.2314152,127.1882944));
        routes.add(new LatLng(37.2316614,127.1883452));
        routes.add(new LatLng(37.2318373,127.1883772));
        routes.add(new LatLng(37.2319726,127.1884026));
        routes.add(new LatLng(37.2321087,127.1884268));
        routes.add(new LatLng(37.2322494,127.1884522));
        routes.add(new LatLng(37.2324189,127.1884774));
        routes.add(new LatLng(37.2325650,127.1884960));
        routes.add(new LatLng(37.2327409,127.1885189));
        routes.add(new LatLng(37.2328887,127.1885352));
        routes.add(new LatLng(37.2329915,127.1885494));
        routes.add(new LatLng(37.2335768,127.1886361));
        routes.add(new LatLng(37.2336309,127.1886449));
        routes.add(new LatLng(37.2336516,127.1886482));
        routes.add(new LatLng(37.2337481,127.1886647));
        routes.add(new LatLng(37.2340033,127.1887008));
        routeInfo.get("이마트·상공회의소").put("진입로", routes);

        // 진입로 ~ 명지대역
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2340033,127.1887008));
        routes.add(new LatLng(37.2342179,127.1887315));
        routes.add(new LatLng(37.2344659,127.1887655));
        routes.add(new LatLng(37.2346435,127.1887929));
        routes.add(new LatLng(37.2347968,127.1888182));
        routes.add(new LatLng(37.2352324,127.1888999));
        routes.add(new LatLng(37.2357061,127.1890456));
        routes.add(new LatLng(37.2357999,127.1890745));
        routes.add(new LatLng(37.2359984,127.1891481));
        routes.add(new LatLng(37.2360246,127.1891581));
        routes.add(new LatLng(37.2360615,127.1891715));
        routes.add(new LatLng(37.2362014,127.1892228));
        routes.add(new LatLng(37.2362402,127.1892351));
        routes.add(new LatLng(37.2362664,127.1892440));
        routes.add(new LatLng(37.2368915,127.1894387));
        routes.add(new LatLng(37.2370368,127.1894843));
        routes.add(new LatLng(37.2371243,127.1895121));
        routes.add(new LatLng(37.2371847,127.1895311));
        routes.add(new LatLng(37.2373065,127.1895700));
        routes.add(new LatLng(37.2377368,127.1897058));
        routes.add(new LatLng(37.2378677,127.1897470));
        routes.add(new LatLng(37.2379498,127.1897748));
        routes.add(new LatLng(37.2382339,127.1898526));
        routes.add(new LatLng(37.2382465,127.1898548));
        routes.add(new LatLng(37.2383169,127.1898748));
        routes.add(new LatLng(37.2383554,127.1897720));
        routes.add(new LatLng(37.2383679,127.1897359));
        routes.add(new LatLng(37.2384125,127.1895632));
        routeInfo.get("진입로").put("명지대역", routes);

        // 명지대역 ~ 진입로(명지대방향)
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2384125,127.1895632));
        routes.add(new LatLng(37.2384911,127.1892585));
        routes.add(new LatLng(37.2385426,127.1889697));
        routes.add(new LatLng(37.2385531,127.1888591));
        routes.add(new LatLng(37.2385749,127.1885738));
        routes.add(new LatLng(37.2386561,127.1875870));
        routes.add(new LatLng(37.2386993,127.1865451));
        routes.add(new LatLng(37.2387124,127.1863759));
        routes.add(new LatLng(37.2387245,127.1861864));
        routes.add(new LatLng(37.2386595,127.1861461));
        routes.add(new LatLng(37.2384329,127.1860039));
        routes.add(new LatLng(37.2379833,127.1857374));
        routes.add(new LatLng(37.2376700,127.1855628));
        routes.add(new LatLng(37.2374832,127.1854610));
        routes.add(new LatLng(37.2373306,127.1853624));
        routes.add(new LatLng(37.2369839,127.1851621));
        routes.add(new LatLng(37.2369500,127.1853110));
        routes.add(new LatLng(37.2369082,127.1854995));
        routes.add(new LatLng(37.2368984,127.1855446));
        routes.add(new LatLng(37.2368832,127.1856123));
        routes.add(new LatLng(37.2368290,127.1858775));
        routes.add(new LatLng(37.2365664,127.1867963));
        routes.add(new LatLng(37.2363098,127.1879450));
        routes.add(new LatLng(37.2362929,127.1880297));
        routes.add(new LatLng(37.2361390,127.1887890));
        routes.add(new LatLng(37.2361069,127.1889492));
        routes.add(new LatLng(37.2360758,127.1891016));
        routes.add(new LatLng(37.2360615,127.1891715));
        routes.add(new LatLng(37.2360246,127.1891581));
        routes.add(new LatLng(37.2359984,127.1891481));
        routes.add(new LatLng(37.2357999,127.1890745));
        routes.add(new LatLng(37.2357061,127.1890456));
        routes.add(new LatLng(37.2352324,127.1888999));
        routes.add(new LatLng(37.2347968,127.1888182));
        routes.add(new LatLng(37.2346435,127.1887929));
        routes.add(new LatLng(37.2344659,127.1887655));
        routes.add(new LatLng(37.2342179,127.1887315));
        routes.add(new LatLng(37.2339916,127.1886998));
        routeInfo.get("명지대역").put("진입로(명지대방향)", routes);

        // 진입로(명지대방향) ~ 이마트
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2339916,127.1886998));
        routes.add(new LatLng(37.2337481,127.1886647));
        routes.add(new LatLng(37.2336516,127.1886482));
        routes.add(new LatLng(37.2336309,127.1886449));
        routes.add(new LatLng(37.2335768,127.1886361));
        routes.add(new LatLng(37.2329915,127.1885494));
        routes.add(new LatLng(37.2328887,127.1885352));
        routes.add(new LatLng(37.2327409,127.1885189));
        routes.add(new LatLng(37.2325650,127.1884960));
        routes.add(new LatLng(37.2324189,127.1884774));
        routes.add(new LatLng(37.2322494,127.1884522));
        routes.add(new LatLng(37.2320213,127.1884114));
        routes.add(new LatLng(37.2319726,127.1884026));
        routes.add(new LatLng(37.2316614,127.1883452));
        routes.add(new LatLng(37.2314152,127.1882944));
        routes.add(new LatLng(37.2312628,127.1882612));
        routes.add(new LatLng(37.2311906,127.1882457));
        routes.add(new LatLng(37.2311203,127.1882313));
        routes.add(new LatLng(37.2310247,127.1882114));
        routes.add(new LatLng(37.2307884,127.1881684));
        routes.add(new LatLng(37.2306793,127.1881464));
        routes.add(new LatLng(37.2305674,127.1881254));
        routes.add(new LatLng(37.2304322,127.1880955));
        routes.add(new LatLng(37.2303582,127.1880812));
        routeInfo.get("진입로(명지대방향)").put("이마트·상공회의소(명지대방향)", routes);

        // 이마트 ~ 명진당
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2303582,127.1880812));
        routes.add(new LatLng(37.2296124,127.1879366));
        routes.add(new LatLng(37.2291984,127.1878548));
        routes.add(new LatLng(37.2290658,127.1878272));
        routes.add(new LatLng(37.2284102,127.1877048));
        routes.add(new LatLng(37.2283831,127.1876993));
        routes.add(new LatLng(37.2281640,127.1876573));
        routes.add(new LatLng(37.2279647,127.1876198));
        routes.add(new LatLng(37.2279250,127.1876121));
        routes.add(new LatLng(37.2277158,127.1875882));
        routes.add(new LatLng(37.2277077,127.1875871));
        routes.add(new LatLng(37.2275085,127.1875913));
        routes.add(new LatLng(37.2271750,127.1875994));
        routes.add(new LatLng(37.2266912,127.1877220));
        routes.add(new LatLng(37.2266831,127.1877243));
        routes.add(new LatLng(37.2263498,127.1877990));
        routes.add(new LatLng(37.2262246,127.1878367));
        routes.add(new LatLng(37.2258247,127.1879623));
        routes.add(new LatLng(37.2257184,127.1879876));
        routes.add(new LatLng(37.2257040,127.1879899));
        routes.add(new LatLng(37.2255995,127.1879959));
        routes.add(new LatLng(37.2255003,127.1879986));
        routes.add(new LatLng(37.2253704,127.1879642));
        routes.add(new LatLng(37.2252666,127.1879161));
        routes.add(new LatLng(37.2252513,127.1879083));
        routes.add(new LatLng(37.2251556,127.1878625));
        routes.add(new LatLng(37.2251547,127.1878625));
        routes.add(new LatLng(37.2251195,127.1878457));
        routes.add(new LatLng(37.2250401,127.1878100));
        routes.add(new LatLng(37.2250428,127.1877953));
        routes.add(new LatLng(37.2250418,127.1877773));
        routes.add(new LatLng(37.2250355,127.1877570));
        routes.add(new LatLng(37.2250273,127.1877424));
        routes.add(new LatLng(37.2250156,127.1877312));
        routes.add(new LatLng(37.2250020,127.1877245));
        routes.add(new LatLng(37.2249867,127.1877234));
        routes.add(new LatLng(37.2249741,127.1877246));
        routes.add(new LatLng(37.2249678,127.1877280));
        routes.add(new LatLng(37.2249624,127.1877303));
        routes.add(new LatLng(37.2249498,127.1877461));
        routes.add(new LatLng(37.2249399,127.1877743));
        routes.add(new LatLng(37.2249382,127.1877901));
        routes.add(new LatLng(37.2248940,127.1877903));
        routes.add(new LatLng(37.2248237,127.1877917));
        routes.add(new LatLng(37.2247813,127.1877919));
        routes.add(new LatLng(37.2246984,127.1878013));
        routes.add(new LatLng(37.2246930,127.1878024));
        routes.add(new LatLng(37.2246345,127.1878128));
        routes.add(new LatLng(37.2242795,127.1878977));
        routes.add(new LatLng(37.2241642,127.1879241));
        routes.add(new LatLng(37.2237579,127.1880182));
        routes.add(new LatLng(37.2235453,127.1880664));
        routes.add(new LatLng(37.2234291,127.1880849));
        routes.add(new LatLng(37.2233155,127.1880899));
        routes.add(new LatLng(37.2232049,127.1881895));
        routes.add(new LatLng(37.2226888,127.1886572));
        routes.add(new LatLng(37.2226357,127.1887059));
        routes.add(new LatLng(37.2222445,127.1890580));
        routeInfo.get("이마트·상공회의소(명지대방향)").put("명진당", routes);

        // 명진당 ~ 제3공학관
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2222445,127.1890580));
        routes.add(new LatLng(37.2220829, 127.1891526));
        routes.add(new LatLng(37.2212713, 127.1875192));
        routes.add(new LatLng(37.2210687,127.1868469));
        routes.add(new LatLng(37.2210497,127.1868244));
        routes.add(new LatLng(37.2209666,127.1867864));
        routes.add(new LatLng(37.2208909,127.1867743));
        routes.add(new LatLng(37.2207412,127.1867659));
        routes.add(new LatLng(37.2206583,127.1867618));
        routes.add(new LatLng(37.2205303,127.1867623));
        routes.add(new LatLng(37.2205078,127.1867624));
        routes.add(new LatLng(37.2203661,127.1867122));
        routes.add(new LatLng(37.2202414,127.1865989));
        routes.add(new LatLng(37.2201582,127.1864989));
        routes.add(new LatLng(37.2200749,127.1863370));
        routes.add(new LatLng(37.2199200,127.1857042));
        routes.add(new LatLng(37.2198799,127.1855409));
        routes.add(new LatLng(37.2198415,127.1853517));
        routes.add(new LatLng(37.2197575,127.1849373));
        routes.add(new LatLng(37.2196493,127.1842490));
        routes.add(new LatLng(37.2196184,127.1841511));
        routes.add(new LatLng(37.2195138,127.1838280));
        routes.add(new LatLng(37.2194862,127.1836185));
        routes.add(new LatLng(37.2194853,127.1836084));
        routes.add(new LatLng(37.2194667,127.1833988));
        routes.add(new LatLng(37.2194663,127.1832489));
        routes.add(new LatLng(37.2195019,127.1830661));
        routes.add(new LatLng(37.2195206,127.1829950));
        routeInfo.get("명진당").put("제3공학관", routes);

        // 진입로 ~ 동부 경찰서
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2340033,127.1887008));
        routes.add(new LatLng(37.2342179,127.1887315));
        routes.add(new LatLng(37.2344659,127.1887655));
        routes.add(new LatLng(37.2346435,127.1887929));
        routes.add(new LatLng(37.2347968,127.1888182));
        routes.add(new LatLng(37.2352324,127.1888999));
        routes.add(new LatLng(37.2357061,127.1890456));
        routes.add(new LatLng(37.2357999,127.1890745));
        routes.add(new LatLng(37.2359984,127.1891481));
        routes.add(new LatLng(37.2360246,127.1891581));
        routes.add(new LatLng(37.2360615,127.1891715));
        routes.add(new LatLng(37.2360144,127.1894040));
        routes.add(new LatLng(37.2359823,127.1895631));
        routes.add(new LatLng(37.2357697,127.1906146));
        routes.add(new LatLng(37.2354982,127.1919551));
        routes.add(new LatLng(37.2354822,127.1920397));
        routes.add(new LatLng(37.2354564,127.1921773));
        routes.add(new LatLng(37.2354075,127.1924301));
        routes.add(new LatLng(37.2351789,127.1936181));
        routes.add(new LatLng(37.2351576,127.1937490));
        routes.add(new LatLng(37.2351337,127.1938833));
        routes.add(new LatLng(37.2350586,127.1944743));
        routes.add(new LatLng(37.2350380,127.1945601));
        routes.add(new LatLng(37.2350095,127.1946831));
        routes.add(new LatLng(37.2349408,127.1949494));
        routes.add(new LatLng(37.2348577,127.1952248));
        routes.add(new LatLng(37.2348165,127.1953242));
        routes.add(new LatLng(37.2347493,127.1954868));
        routes.add(new LatLng(37.2346561,127.1957239));
        routes.add(new LatLng(37.2346266,127.1957996));
        routes.add(new LatLng(37.2347043,127.1958759));
        routes.add(new LatLng(37.2347487,127.1959468));
        routes.add(new LatLng(37.2347705,127.1960154));
        routes.add(new LatLng(37.2347869,127.1960887));
        routes.add(new LatLng(37.2347988,127.1961698));
        routes.add(new LatLng(37.2348144,127.1962858));
        routes.add(new LatLng(37.2348172,127.1963106));
        routes.add(new LatLng(37.2348200,127.1963467));
        routes.add(new LatLng(37.2348211,127.1964188));
        routes.add(new LatLng(37.2348163,127.1966624));
        routes.add(new LatLng(37.2348357,127.1975597));
        routes.add(new LatLng(37.2348380,127.1977446));
        routes.add(new LatLng(37.2348395,127.1979689));
        routes.add(new LatLng(37.2348436,127.1981718));
        routeInfo.get("진입로").put("동부경찰서", routes);

        // 동부경찰서 ~ 용인 시장
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2348436,127.1981718));
        routes.add(new LatLng(37.2348498,127.1984999));
        routes.add(new LatLng(37.2348565,127.1989970));
        routes.add(new LatLng(37.2348577,127.1991120));
        routes.add(new LatLng(37.2348660,127.1999124));
        routes.add(new LatLng(37.2348672,127.2000217));
        routes.add(new LatLng(37.2348751,127.2006440));
        routes.add(new LatLng(37.2348772,127.2007534));
        routes.add(new LatLng(37.2348882,127.2015335));
        routes.add(new LatLng(37.2348875,127.2016462));
        routes.add(new LatLng(37.2348993,127.2023620));
        routes.add(new LatLng(37.2349032,127.2025007));
        routes.add(new LatLng(37.2349187,127.2032932));
        routes.add(new LatLng(37.2349577,127.2037372));
        routes.add(new LatLng(37.2350088,127.2039895));
        routes.add(new LatLng(37.2350289,127.2040898));
        routes.add(new LatLng(37.2350499,127.2041991));
        routes.add(new LatLng(37.2351714,127.2048434));
        routes.add(new LatLng(37.2352024,127.2050068));
        routes.add(new LatLng(37.2353220,127.2056061));
        routes.add(new LatLng(37.2353366,127.2056782));
        routes.add(new LatLng(37.2353458,127.2057266));
        routes.add(new LatLng(37.2353531,127.2057638));
        routes.add(new LatLng(37.2353658,127.2058246));
        routes.add(new LatLng(37.2354334,127.2061806));
        routes.add(new LatLng(37.2355047,127.2065535));
        routes.add(new LatLng(37.2355184,127.2066583));
        routeInfo.get("동부경찰서").put("용인시장", routes);

        //용인시장 ~ 중앙공영주차장
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2355184,127.2066583));
        routes.add(new LatLng(37.2355910,127.2072240));
        routes.add(new LatLng(37.2356003,127.2073367));
        routes.add(new LatLng(37.2356003,127.2073412));
        routes.add(new LatLng(37.2356172,127.2076106));
        routes.add(new LatLng(37.2355902,127.2079613));
        routes.add(new LatLng(37.2355823,127.2080583));
        routes.add(new LatLng(37.2355667,127.2083244));
        routes.add(new LatLng(37.2355557,127.2085984));
        routes.add(new LatLng(37.2355549,127.2086423));
        routes.add(new LatLng(37.2355472,127.2088070));
        routes.add(new LatLng(37.2353597,127.2088336));
        routes.add(new LatLng(37.2352669,127.2088407));
        routes.add(new LatLng(37.2347956,127.2088967));
        routes.add(new LatLng(37.2346785,127.2089084));
        routes.add(new LatLng(37.2345199,127.2089338));
        routes.add(new LatLng(37.2344199,127.2089477));
        routes.add(new LatLng(37.2339179,127.2089891));
        routeInfo.get("용인시장").put("중앙공영주차장", routes);

        // 중앙공영주차장 ~ 진입로(명지대방향)
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2339179,127.2089891));
        routes.add(new LatLng(37.2334961,127.2090245));
        routes.add(new LatLng(37.2334051,127.2090350));
        routes.add(new LatLng(37.2333826,127.2090385));
        routes.add(new LatLng(37.2333240,127.2090489));
        routes.add(new LatLng(37.2332528,127.2090582));
        routes.add(new LatLng(37.2332425,127.2088970));
        routes.add(new LatLng(37.2332369,127.2088136));
        routes.add(new LatLng(37.2332106,127.2083774));
        routes.add(new LatLng(37.2331815,127.2079052));
        routes.add(new LatLng(37.2331749,127.2077992));
        routes.add(new LatLng(37.2330968,127.2064625));
        routes.add(new LatLng(37.2330816,127.2061424));
        routes.add(new LatLng(37.2330122,127.2046940));
        routes.add(new LatLng(37.2329034,127.2044780));
        routes.add(new LatLng(37.2328910,127.2041872));
        routes.add(new LatLng(37.2328898,127.2040745));
        routes.add(new LatLng(37.2328776,127.2035232));
        routes.add(new LatLng(37.2328790,127.2033609));
        routes.add(new LatLng(37.2328788,127.2032741));
        routes.add(new LatLng(37.2328800,127.2030430));
        routes.add(new LatLng(37.2328832,127.2024996));
        routes.add(new LatLng(37.2328829,127.2023993));
        routes.add(new LatLng(37.2328844,127.2022865));
        routes.add(new LatLng(37.2328875,127.2013373));
        routes.add(new LatLng(37.2328912,127.2010239));
        routes.add(new LatLng(37.2328867,127.2006801));
        routes.add(new LatLng(37.2328855,127.2005437));
        routes.add(new LatLng(37.2328831,127.2003374));
        routes.add(new LatLng(37.2328856,127.2002393));
        routes.add(new LatLng(37.2328881,127.2001637));
        routes.add(new LatLng(37.2328969,127.2000758));
        routes.add(new LatLng(37.2329040,127.2000205));
        routes.add(new LatLng(37.2329243,127.1998750));
        routes.add(new LatLng(37.2329660,127.1995997));
        routes.add(new LatLng(37.2332329,127.1989121));
        routes.add(new LatLng(37.2332741,127.1988116));
        routes.add(new LatLng(37.2334039,127.1984842));
        routes.add(new LatLng(37.2334075,127.1984740));
        routes.add(new LatLng(37.2335446,127.1981590));
        routes.add(new LatLng(37.2338995,127.1973368));
        routes.add(new LatLng(37.2340384,127.1970319));
        routes.add(new LatLng(37.2343324,127.1963870));
        routes.add(new LatLng(37.2344490,127.1961486));
        routes.add(new LatLng(37.2345100,127.1960289));
        routes.add(new LatLng(37.2345620,127.1959272));
        routes.add(new LatLng(37.2346051,127.1958425));
        routes.add(new LatLng(37.2346266,127.1957996));
        routes.add(new LatLng(37.2347493,127.1954868));
        routes.add(new LatLng(37.2348165,127.1953242));
        routes.add(new LatLng(37.2348577,127.1952248));
        routes.add(new LatLng(37.2349408,127.1949494));
        routes.add(new LatLng(37.2350095,127.1946831));
        routes.add(new LatLng(37.2350380,127.1945601));
        routes.add(new LatLng(37.2350586,127.1944743));
        routes.add(new LatLng(37.2351570,127.1942022));
        routes.add(new LatLng(37.2352338,127.1939178));
        routes.add(new LatLng(37.2352614,127.1937801));
        routes.add(new LatLng(37.2352881,127.1936459));
        routes.add(new LatLng(37.2354678,127.1927511));
        routes.add(new LatLng(37.2355275,127.1924533));
        routes.add(new LatLng(37.2355746,127.1922175));
        routes.add(new LatLng(37.2356040,127.1920764));
        routes.add(new LatLng(37.2356663,127.1917706));
        routes.add(new LatLng(37.2357455,127.1913746));
        routes.add(new LatLng(37.2358684,127.1907856));
        routes.add(new LatLng(37.2361204,127.1896211));
        routes.add(new LatLng(37.2361542,127.1894519));
        routes.add(new LatLng(37.2361871,127.1892894));
        routes.add(new LatLng(37.2360246,127.1891581));
        routes.add(new LatLng(37.2359984,127.1891481));
        routes.add(new LatLng(37.2357999,127.1890745));
        routes.add(new LatLng(37.2357061,127.1890456));
        routes.add(new LatLng(37.2352324,127.1888999));
        routes.add(new LatLng(37.2347968,127.1888182));
        routes.add(new LatLng(37.2346435,127.1887929));
        routes.add(new LatLng(37.2344659,127.1887655));
        routes.add(new LatLng(37.2342179,127.1887315));
        routes.add(new LatLng(37.2339916,127.1886998));
        routeInfo.get("중앙공영주차장").put("진입로(명지대방향)", routes);

        // 이마트 ~ 제1공학관
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2303582,127.1880812));
        routes.add(new LatLng(37.2296124,127.1879366));
        routes.add(new LatLng(37.2291984,127.1878548));
        routes.add(new LatLng(37.2290658,127.1878272));
        routes.add(new LatLng(37.2284102,127.1877048));
        routes.add(new LatLng(37.2283831,127.1876993));
        routes.add(new LatLng(37.2281640,127.1876573));
        routes.add(new LatLng(37.2279647,127.1876198));
        routes.add(new LatLng(37.2279250,127.1876121));
        routes.add(new LatLng(37.2277158,127.1875882));
        routes.add(new LatLng(37.2277077,127.1875871));
        routes.add(new LatLng(37.2275085,127.1875913));
        routes.add(new LatLng(37.2271750,127.1875994));
        routes.add(new LatLng(37.2266912,127.1877220));
        routes.add(new LatLng(37.2266831,127.1877243));
        routes.add(new LatLng(37.2263498,127.1877990));
        routes.add(new LatLng(37.2262246,127.1878367));
        routes.add(new LatLng(37.2258247,127.1879623));
        routes.add(new LatLng(37.2257184,127.1879876));
        routes.add(new LatLng(37.2257040,127.1879899));
        routes.add(new LatLng(37.2255995,127.1879959));
        routes.add(new LatLng(37.2255003,127.1879986));
        routes.add(new LatLng(37.2253704,127.1879642));
        routes.add(new LatLng(37.2252666,127.1879161));
        routes.add(new LatLng(37.2252513,127.1879083));
        routes.add(new LatLng(37.2251556,127.1878625));
        routes.add(new LatLng(37.2251547,127.1878625));
        routes.add(new LatLng(37.2251195,127.1878457));
        routes.add(new LatLng(37.2250401,127.1878100));
        routes.add(new LatLng(37.2250428,127.1877953));
        routes.add(new LatLng(37.2250418,127.1877773));
        routes.add(new LatLng(37.2250355,127.1877570));
        routes.add(new LatLng(37.2250273,127.1877424));
        routes.add(new LatLng(37.2250156,127.1877312));
        routes.add(new LatLng(37.2250020,127.1877245));
        routes.add(new LatLng(37.2249867,127.1877234));
        routes.add(new LatLng(37.2249741,127.1877246));
        routes.add(new LatLng(37.2249678,127.1877280));
        routes.add(new LatLng(37.2249624,127.1877303));
        routes.add(new LatLng(37.2249498,127.1877461));
        routes.add(new LatLng(37.2249399,127.1877743));
        routes.add(new LatLng(37.2249382,127.1877901));
        routes.add(new LatLng(37.2248940,127.1877903));
        routes.add(new LatLng(37.2248237,127.1877917));
        routes.add(new LatLng(37.2247813,127.1877919));
        routes.add(new LatLng(37.2246984,127.1878013));
        routes.add(new LatLng(37.2246930,127.1878024));
        routes.add(new LatLng(37.2246345,127.1878128));
        routes.add(new LatLng(37.2242795,127.1878977));
        routes.add(new LatLng(37.2241642,127.1879241));
        routes.add(new LatLng(37.2237579,127.1880182));
        routes.add(new LatLng(37.2235453,127.1880664));
        routes.add(new LatLng(37.2234291,127.1880849));
        routes.add(new LatLng(37.2233155,127.1880899));
        routes.add(new LatLng(37.2232666,127.1879853));
        routes.add(new LatLng(37.2231134,127.1876567));
        routes.add(new LatLng(37.2230174,127.1874790));
        routes.add(new LatLng(37.2226740,127.1868132));
        routeInfo.get("이마트·상공회의소(명지대방향)").put("제1공학관", routes);

        // 제1공학관 ~ 제3공학관
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2226740,127.1868132));
        routes.add(new LatLng(37.2226278,127.1867232));
        routes.add(new LatLng(37.2225081,127.1864622));
        routes.add(new LatLng(37.2223919,127.1864807));
        routes.add(new LatLng(37.2221580,127.1866845));
        routes.add(new LatLng(37.2218861,127.1864366));
        routes.add(new LatLng(37.2218103,127.1864042));
        routes.add(new LatLng(37.2217491,127.1864416));
        routes.add(new LatLng(37.2215492,127.1865495));
        routes.add(new LatLng(37.2213701,127.1866461));
        routes.add(new LatLng(37.2212576,127.1867243));
        routes.add(new LatLng(37.2211748,127.1867743));
        routes.add(new LatLng(37.2210759,127.1868558));
        routes.add(new LatLng(37.2210497,127.1868244));
        routes.add(new LatLng(37.2209666,127.1867864));
        routes.add(new LatLng(37.2208909,127.1867743));
        routes.add(new LatLng(37.2207412,127.1867659));
        routes.add(new LatLng(37.2206583,127.1867618));
        routes.add(new LatLng(37.2205303,127.1867623));
        routes.add(new LatLng(37.2205078,127.1867624));
        routes.add(new LatLng(37.2203661,127.1867122));
        routes.add(new LatLng(37.2202414,127.1865989));
        routes.add(new LatLng(37.2201582,127.1864989));
        routes.add(new LatLng(37.2200749,127.1863370));
        routes.add(new LatLng(37.2199200,127.1857042));
        routes.add(new LatLng(37.2198799,127.1855409));
        routes.add(new LatLng(37.2198415,127.1853517));
        routes.add(new LatLng(37.2197575,127.1849373));
        routes.add(new LatLng(37.2196493,127.1842490));
        routes.add(new LatLng(37.2196184,127.1841511));
        routes.add(new LatLng(37.2195138,127.1838280));
        routes.add(new LatLng(37.2194862,127.1836185));
        routes.add(new LatLng(37.2194853,127.1836084));
        routes.add(new LatLng(37.2194667,127.1833988));
        routes.add(new LatLng(37.2194663,127.1832489));
        routes.add(new LatLng(37.2195019,127.1830661));
        routes.add(new LatLng(37.2195206,127.1829950));
        routeInfo.get("제1공학관").put("제3공학관", routes);

        // 명현관 ~ 함박관
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2234041,127.1813985));
        routes.add(new LatLng(37.2232673,127.1814655));
        routes.add(new LatLng(37.2231629,127.1815314));
        routes.add(new LatLng(37.2230928,127.1815982));
        routes.add(new LatLng(37.2230362,127.1816897));
        routes.add(new LatLng(37.2227373,127.1814723));
        routes.add(new LatLng(37.2225658,127.1814166));
        routes.add(new LatLng(37.2223890,127.1813621));
        routes.add(new LatLng(37.2222582,127.1813108));
        routes.add(new LatLng(37.2221933,127.1813066));
        routes.add(new LatLng(37.2218519,127.1814219));
        routes.add(new LatLng(37.2216709,127.1814756));
        routes.add(new LatLng(37.2215933,127.1814602));
        routes.add(new LatLng(37.2214842,127.1814223));
        routes.add(new LatLng(37.2214066,127.1813967));
        routes.add(new LatLng(37.2213597,127.1813868));
        routes.add(new LatLng(37.2213092,127.1813870));
        routes.add(new LatLng(37.2212525,127.1814041));
        routes.add(new LatLng(37.2210931,127.1814747));
        routes.add(new LatLng(37.2208841,127.1815206));
        routes.add(new LatLng(37.2207597,127.1815291));
        routes.add(new LatLng(37.2206354,127.1815600));
        routes.add(new LatLng(37.2204327,127.1815981));
        routes.add(new LatLng(37.2202488,127.1816033));
        routes.add(new LatLng(37.2201858,127.1816138));
        routes.add(new LatLng(37.2200723,127.1816582));
        routes.add(new LatLng(37.2200156,127.1817024));
        routes.add(new LatLng(37.2199896,127.1817498));
        routes.add(new LatLng(37.2199137,127.1820094));
        routes.add(new LatLng(37.2197586,127.1823099));
        routes.add(new LatLng(37.2196922,127.1824556));
        routes.add(new LatLng(37.2195733,127.1827998));
        routes.add(new LatLng(37.2195287,127.1829668));
        routes.add(new LatLng(37.2195206,127.1829950));
        routeInfo.get("명현관").put("함박관", routes);

        // 함박관 ~ 명지대 정문
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2195206,127.1829950));
        routes.add(new LatLng(37.2195019,127.1830661));
        routes.add(new LatLng(37.2194663,127.1832489));
        routes.add(new LatLng(37.2194667,127.1833988));
        routes.add(new LatLng(37.2194853,127.1836084));
        routes.add(new LatLng(37.2194862,127.1836185));
        routes.add(new LatLng(37.2195138,127.1838280));
        routes.add(new LatLng(37.2196175,127.1841511));
        routes.add(new LatLng(37.2196493,127.1842490));
        routes.add(new LatLng(37.2197575,127.1849373));
        routes.add(new LatLng(37.2198415,127.1853517));
        routes.add(new LatLng(37.2198799,127.1855409));
        routes.add(new LatLng(37.2199200,127.1857042));
        routes.add(new LatLng(37.2200749,127.1863370));
        routes.add(new LatLng(37.2201582,127.1864989));
        routes.add(new LatLng(37.2202414,127.1865989));
        routes.add(new LatLng(37.2203661,127.1867122));
        routes.add(new LatLng(37.2205078,127.1867624));
        routes.add(new LatLng(37.2205303,127.1867623));
        routes.add(new LatLng(37.2206583,127.1867618));
        routes.add(new LatLng(37.2207412,127.1867659));
        routes.add(new LatLng(37.2208909,127.1867743));
        routes.add(new LatLng(37.2209666,127.1867864));
        routes.add(new LatLng(37.2210497,127.1868244));
        routes.add(new LatLng(37.2210759,127.1868558));
        routes.add(new LatLng(37.2211076,127.1869121));
        routes.add(new LatLng(37.2211748,127.1870989));
        routes.add(new LatLng(37.2212079,127.1873366));
        routes.add(new LatLng(37.2212325,127.1874616));
        routes.add(new LatLng(37.2212924,127.1876147));
        routes.add(new LatLng(37.2213930,127.1878160));
        routes.add(new LatLng(37.2214819,127.1880118));
        routes.add(new LatLng(37.2216233,127.1883245));
        routes.add(new LatLng(37.2216913,127.1884742));
        routes.add(new LatLng(37.2219830,127.1890084));
        routes.add(new LatLng(37.2220871,127.1892007));
        routes.add(new LatLng(37.2221312,127.1891611));
        routes.add(new LatLng(37.2226348,127.1887059));
        routes.add(new LatLng(37.2226888,127.1886572));
        routes.add(new LatLng(37.2232049,127.1881895));
        routes.add(new LatLng(37.2233155,127.1880899));
        routes.add(new LatLng(37.2234291,127.1880849));
        routes.add(new LatLng(37.2235453,127.1880664));
        routes.add(new LatLng(37.2237579,127.1880182));
        routes.add(new LatLng(37.2241642,127.1879241));
        routes.add(new LatLng(37.2241678,127.1879241));
        routeInfo.get("함박관").put("명지대 정문", routes);

        // 명지대 정문 ~ 상공회의소
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2241678,127.1879241));
        routes.add(new LatLng(37.2242795,127.1878977));
        routes.add(new LatLng(37.2246345,127.1878128));
        routes.add(new LatLng(37.2246930,127.1878024));
        routes.add(new LatLng(37.2246984,127.1878013));
        routes.add(new LatLng(37.2247813,127.1877919));
        routes.add(new LatLng(37.2248237,127.1877917));
        routes.add(new LatLng(37.2248940,127.1877903));
        routes.add(new LatLng(37.2249382,127.1877901));
        routes.add(new LatLng(37.2249400,127.1878014));
        routes.add(new LatLng(37.2249446,127.1878217));
        routes.add(new LatLng(37.2249545,127.1878385));
        routes.add(new LatLng(37.2249681,127.1878497));
        routes.add(new LatLng(37.2249825,127.1878564));
        routes.add(new LatLng(37.2250006,127.1878564));
        routes.add(new LatLng(37.2250141,127.1878507));
        routes.add(new LatLng(37.2250267,127.1878405));
        routes.add(new LatLng(37.2250383,127.1878213));
        routes.add(new LatLng(37.2250401,127.1878100));
        routes.add(new LatLng(37.2251195,127.1878457));
        routes.add(new LatLng(37.2251547,127.1878625));
        routes.add(new LatLng(37.2251556,127.1878625));
        routes.add(new LatLng(37.2252513,127.1879083));
        routes.add(new LatLng(37.2252666,127.1879161));
        routes.add(new LatLng(37.2253704,127.1879642));
        routes.add(new LatLng(37.2255003,127.1879986));
        routes.add(new LatLng(37.2255995,127.1879959));
        routes.add(new LatLng(37.2257040,127.1879899));
        routes.add(new LatLng(37.2257184,127.1879876));
        routes.add(new LatLng(37.2258247,127.1879623));
        routes.add(new LatLng(37.2262246,127.1878367));
        routes.add(new LatLng(37.2263498,127.1877990));
        routes.add(new LatLng(37.2266831,127.1877243));
        routes.add(new LatLng(37.2271750,127.1875994));
        routes.add(new LatLng(37.2275085,127.1875913));
        routes.add(new LatLng(37.2277077,127.1875871));
        routes.add(new LatLng(37.2277158,127.1875882));
        routes.add(new LatLng(37.2279250,127.1876121));
        routes.add(new LatLng(37.2279647,127.1876198));
        routes.add(new LatLng(37.2281640,127.1876573));
        routes.add(new LatLng(37.2283831,127.1876993));
        routes.add(new LatLng(37.2284102,127.1877048));
        routes.add(new LatLng(37.2290126,127.1878173));
        routes.add(new LatLng(37.2290658,127.1878272));
        routes.add(new LatLng(37.2291984,127.1878548));
        routes.add(new LatLng(37.2296124,127.1879366));
        routes.add(new LatLng(37.2304322,127.1880955));
        routes.add(new LatLng(37.2305674,127.1881254));
        routes.add(new LatLng(37.2306793,127.1881464));
        routes.add(new LatLng(37.2306919,127.1881486));
        routeInfo.get("명지대 정문").put("이마트·상공회의소", routes);

        // 명지대 ~ 기흥역
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2242590,127.1872767));
        routes.add(new LatLng(37.2242341,127.1873974));
        routes.add(new LatLng(37.2242208,127.1874831));
        routes.add(new LatLng(37.2242236,127.1875428));
        routes.add(new LatLng(37.2242653,127.1876294));
        routes.add(new LatLng(37.2243034,127.1877093));
        routes.add(new LatLng(37.2243116,127.1877532));
        routes.add(new LatLng(37.2242795,127.1878977));
        routes.add(new LatLng(37.2246345,127.1878128));
        routes.add(new LatLng(37.2246930,127.1878024));
        routes.add(new LatLng(37.2246984,127.1878013));
        routes.add(new LatLng(37.2247813,127.1877919));
        routes.add(new LatLng(37.2248237,127.1877917));
        routes.add(new LatLng(37.2248940,127.1877903));
        routes.add(new LatLng(37.2249382,127.1877901));
        routes.add(new LatLng(37.2249400,127.1878014));
        routes.add(new LatLng(37.2249446,127.1878217));
        routes.add(new LatLng(37.2249545,127.1878385));
        routes.add(new LatLng(37.2249681,127.1878497));
        routes.add(new LatLng(37.2249825,127.1878564));
        routes.add(new LatLng(37.2250006,127.1878564));
        routes.add(new LatLng(37.2250141,127.1878507));
        routes.add(new LatLng(37.2250267,127.1878405));
        routes.add(new LatLng(37.2250383,127.1878213));
        routes.add(new LatLng(37.2250401,127.1878100));
        routes.add(new LatLng(37.2251195,127.1878457));
        routes.add(new LatLng(37.2251547,127.1878625));
        routes.add(new LatLng(37.2251556,127.1878625));
        routes.add(new LatLng(37.2252513,127.1879083));
        routes.add(new LatLng(37.2252666,127.1879161));
        routes.add(new LatLng(37.2253704,127.1879642));
        routes.add(new LatLng(37.2255003,127.1879986));
        routes.add(new LatLng(37.2255995,127.1879959));
        routes.add(new LatLng(37.2257040,127.1879899));
        routes.add(new LatLng(37.2257184,127.1879876));
        routes.add(new LatLng(37.2258247,127.1879623));
        routes.add(new LatLng(37.2262246,127.1878367));
        routes.add(new LatLng(37.2263498,127.1877990));
        routes.add(new LatLng(37.2266831,127.1877243));
        routes.add(new LatLng(37.2271750,127.1875994));
        routes.add(new LatLng(37.2275085,127.1875913));
        routes.add(new LatLng(37.2277077,127.1875871));
        routes.add(new LatLng(37.2277158,127.1875882));
        routes.add(new LatLng(37.2279250,127.1876121));
        routes.add(new LatLng(37.2279647,127.1876198));
        routes.add(new LatLng(37.2281640,127.1876573));
        routes.add(new LatLng(37.2281872,127.1875738));
        routes.add(new LatLng(37.2282506,127.1873391));
        routes.add(new LatLng(37.2284220,127.1867071));
        routes.add(new LatLng(37.2285522,127.1862015));
        routes.add(new LatLng(37.2287242,127.1854501));
        routes.add(new LatLng(37.2288658,127.1848035));
        routes.add(new LatLng(37.2290066,127.1842055));
        routes.add(new LatLng(37.2291353,127.1837856));
        routes.add(new LatLng(37.2291767,127.1833999));
        routes.add(new LatLng(37.2293756,127.1829730));
        routes.add(new LatLng(37.2295898,127.1825133));
        routes.add(new LatLng(37.2299369,127.1818456));
        routes.add(new LatLng(37.2304606,127.1808423));
        routes.add(new LatLng(37.2306418,127.1804966));
        routes.add(new LatLng(37.2308560,127.1800730));
        routes.add(new LatLng(37.2311654,127.1794821));
        routes.add(new LatLng(37.2314129,127.1790087));
        routes.add(new LatLng(37.2318414,127.1781399));
        routes.add(new LatLng(37.2319319,127.1779389));
        routes.add(new LatLng(37.2321962,127.1773425));
        routes.add(new LatLng(37.2323027,127.1770490));
        routes.add(new LatLng(37.2323295,127.1769767));
        routes.add(new LatLng(37.2323421,127.1769417));
        routes.add(new LatLng(37.2325165,127.1764630));
        routes.add(new LatLng(37.2328864,127.1752337));
        routes.add(new LatLng(37.2330809,127.1745328));
        routes.add(new LatLng(37.2331247,127.1743748));
        routes.add(new LatLng(37.2336858,127.1728944));
        routes.add(new LatLng(37.2337260,127.1727905));
        routes.add(new LatLng(37.2339549,127.1721120));
        routes.add(new LatLng(37.2343082,127.1711049));
        routes.add(new LatLng(37.2345580,127.1704826));
        routes.add(new LatLng(37.2349403,127.1695633));
        routes.add(new LatLng(37.2350774,127.1692673));
        routes.add(new LatLng(37.2353498,127.1686776));
        routes.add(new LatLng(37.2360586,127.1671954));
        routes.add(new LatLng(37.2363607,127.1665819));
        routes.add(new LatLng(37.2364153,127.1664700));
        routes.add(new LatLng(37.2368754,127.1656292));
        routes.add(new LatLng(37.2374291,127.1647541));
        routes.add(new LatLng(37.2377137,127.1643729));
        routes.add(new LatLng(37.2382480,127.1636502));
        routes.add(new LatLng(37.2383145,127.1635585));
        routes.add(new LatLng(37.2385084,127.1632927));
        routes.add(new LatLng(37.2389413,127.1627327));
        routes.add(new LatLng(37.2395859,127.1618211));
        routes.add(new LatLng(37.2399091,127.1613811));
        routes.add(new LatLng(37.2401911,127.1609965));
        routes.add(new LatLng(37.2403078,127.1608438));
        routes.add(new LatLng(37.2403967,127.1607205));
        routes.add(new LatLng(37.2405709,127.1604931));
        routes.add(new LatLng(37.2407011,127.1603144));
        routes.add(new LatLng(37.2407891,127.1602001));
        routes.add(new LatLng(37.2411367,127.1597476));
        routes.add(new LatLng(37.2413602,127.1594410));
        routes.add(new LatLng(37.2417104,127.1589659));
        routes.add(new LatLng(37.2421593,127.1583348));
        routes.add(new LatLng(37.2423263,127.1581040));
        routes.add(new LatLng(37.2424888,127.1578778));
        routes.add(new LatLng(37.2427746,127.1575709));
        routes.add(new LatLng(37.2433159,127.1568052));
        routes.add(new LatLng(37.2436750,127.1562962));
        routes.add(new LatLng(37.2442382,127.1553251));
        routes.add(new LatLng(37.2442696,127.1552640));
        routes.add(new LatLng(37.2454083,127.1531966));
        routes.add(new LatLng(37.2465353,127.1511258));
        routes.add(new LatLng(37.2472220,127.1498406));
        routes.add(new LatLng(37.2472901,127.1497140));
        routes.add(new LatLng(37.2481435,127.1481213));
        routes.add(new LatLng(37.2483740,127.1477053));
        routes.add(new LatLng(37.2485405,127.1473268));
        routes.add(new LatLng(37.2485575,127.1472974));
        routes.add(new LatLng(37.2485728,127.1472725));
        routes.add(new LatLng(37.2487190,127.1470283));
        routes.add(new LatLng(37.2498682,127.1449030));
        routes.add(new LatLng(37.2499991,127.1446566));
        routes.add(new LatLng(37.2502232,127.1442361));
        routes.add(new LatLng(37.2509268,127.1429191));
        routes.add(new LatLng(37.2513509,127.1421526));
        routes.add(new LatLng(37.2515517,127.1417998));
        routes.add(new LatLng(37.2515607,127.1417851));
        routes.add(new LatLng(37.2521567,127.1406592));
        routes.add(new LatLng(37.2529538,127.1392639));
        routes.add(new LatLng(37.2532858,127.1387605));
        routes.add(new LatLng(37.2533460,127.1386892));
        routes.add(new LatLng(37.2543330,127.1375218));
        routes.add(new LatLng(37.2547678,127.1370304));
        routes.add(new LatLng(37.2551245,127.1366655));
        routes.add(new LatLng(37.2552980,127.1365113));
        routes.add(new LatLng(37.2554769,127.1363537));
        routes.add(new LatLng(37.2556667,127.1362231));
        routes.add(new LatLng(37.2559474,127.1360380));
        routes.add(new LatLng(37.2561462,127.1359028));
        routes.add(new LatLng(37.2563676,127.1357867));
        routes.add(new LatLng(37.2566845,127.1356544));
        routes.add(new LatLng(37.2568519,127.1355938));
        routes.add(new LatLng(37.2570222,127.1355479));
        routes.add(new LatLng(37.2573257,127.1354697));
        routes.add(new LatLng(37.2579077,127.1353677));
        routes.add(new LatLng(37.2579635,127.1353584));
        routes.add(new LatLng(37.2581906,127.1353190));
        routes.add(new LatLng(37.2596665,127.1351348));
        routes.add(new LatLng(37.2598530,127.1351125));
        routes.add(new LatLng(37.2604179,127.1350331));
        routes.add(new LatLng(37.2606135,127.1350118));
        routes.add(new LatLng(37.2612639,127.1348925));
        routes.add(new LatLng(37.2614495,127.1348522));
        routes.add(new LatLng(37.2616711,127.1348026));
        routes.add(new LatLng(37.2618746,127.1347430));
        routes.add(new LatLng(37.2621285,127.1346538));
        routes.add(new LatLng(37.2624725,127.1345416));
        routes.add(new LatLng(37.2627893,127.1343777));
        routes.add(new LatLng(37.2633659,127.1340038));
        routes.add(new LatLng(37.2636186,127.1338097));
        routes.add(new LatLng(37.2638407,127.1336271));
        routes.add(new LatLng(37.2640186,127.1334492));
        routes.add(new LatLng(37.2641840,127.1332837));
        routes.add(new LatLng(37.2644543,127.1329587));
        routes.add(new LatLng(37.2648019,127.1325488));
        routes.add(new LatLng(37.2648710,127.1324492));
        routes.add(new LatLng(37.2650074,127.1322523));
        routes.add(new LatLng(37.2651678,127.1319707));
        routes.add(new LatLng(37.2653391,127.1316789));
        routes.add(new LatLng(37.2655389,127.1312809));
        routes.add(new LatLng(37.2658720,127.1305620));
        routes.add(new LatLng(37.2660079,127.1302162));
        routes.add(new LatLng(37.2660329,127.1301529));
        routes.add(new LatLng(37.2660741,127.1300478));
        routes.add(new LatLng(37.2665501,127.1289300));
        routes.add(new LatLng(37.2667568,127.1284362));
        routes.add(new LatLng(37.2670020,127.1278722));
        routes.add(new LatLng(37.2670673,127.1277207));
        routes.add(new LatLng(37.2672203,127.1273455));
        routes.add(new LatLng(37.2676838,127.1262774));
        routes.add(new LatLng(37.2680310,127.1254456));
        routes.add(new LatLng(37.2682170,127.1249992));
        routes.add(new LatLng(37.2682868,127.1248341));
        routes.add(new LatLng(37.2686619,127.1240180));
        routes.add(new LatLng(37.2691559,127.1228865));
        routes.add(new LatLng(37.2691872,127.1228153));
        routes.add(new LatLng(37.2694145,127.1222964));
        routes.add(new LatLng(37.2696167,127.1218183));
        routes.add(new LatLng(37.2698574,127.1212487));
        routes.add(new LatLng(37.2702567,127.1206839));
        routes.add(new LatLng(37.2703542,127.1204499));
        routes.add(new LatLng(37.2709857,127.1189331));
        routes.add(new LatLng(37.2710901,127.1186190));
        routes.add(new LatLng(37.2712312,127.1181897));
        routes.add(new LatLng(37.2713506,127.1177785));
        routes.add(new LatLng(37.2713577,127.1177435));
        routes.add(new LatLng(37.2715128,127.1169272));
        routes.add(new LatLng(37.2715950,127.1161215));
        routes.add(new LatLng(37.2716282,127.1157987));
        routes.add(new LatLng(37.2716390,127.1155370));
        routes.add(new LatLng(37.2716423,127.1154231));
        routes.add(new LatLng(37.2716439,127.1153701));
        routes.add(new LatLng(37.2716448,127.1153655));
        routes.add(new LatLng(37.2716553,127.1152786));
        routes.add(new LatLng(37.2716803,127.1151860));
        routes.add(new LatLng(37.2716838,127.1151804));
        routes.add(new LatLng(37.2717251,127.1151238));
        routes.add(new LatLng(37.2717647,127.1150999));
        routes.add(new LatLng(37.2718106,127.1150861));
        routes.add(new LatLng(37.2718575,127.1150768));
        routes.add(new LatLng(37.2719089,127.1150924));
        routes.add(new LatLng(37.2719875,127.1151314));
        routes.add(new LatLng(37.2720102,127.1151967));
        routes.add(new LatLng(37.2720691,127.1153070));
        routes.add(new LatLng(37.2721271,127.1153958));
        routes.add(new LatLng(37.2722294,127.1155115));
        routes.add(new LatLng(37.2723723,127.1156562));
        routes.add(new LatLng(37.2725404,127.1158009));
        routes.add(new LatLng(37.2726705,127.1159085));
        routes.add(new LatLng(37.2727301,127.1159217));
        routes.add(new LatLng(37.2727716,127.1159283));
        routes.add(new LatLng(37.2728599,127.1159369));
        routes.add(new LatLng(37.2733719,127.1159467));
        routes.add(new LatLng(37.2736938,127.1159541));
        routes.add(new LatLng(37.2737965,127.1159569));
        routes.add(new LatLng(37.2737962,127.1158475));
        routes.add(new LatLng(37.2737950,127.1157697));
        routes.add(new LatLng(37.2738105,127.1155248));
        routes.add(new LatLng(37.2738816,127.1152244));
        routes.add(new LatLng(37.2739477,127.1150572));
        routes.add(new LatLng(37.2739934,127.1149532));
        routes.add(new LatLng(37.2743834,127.1143263));
        routes.add(new LatLng(37.2744310,127.1142505));
        routes.add(new LatLng(37.2744898,127.1143133));
        routes.add(new LatLng(37.2746255,127.1144593));
        routes.add(new LatLng(37.2747123,127.1145660));
        routes.add(new LatLng(37.2747223,127.1145817));
        routes.add(new LatLng(37.2747477,127.1146200));
        routes.add(new LatLng(37.2748238,127.1147335));
        routes.add(new LatLng(37.2749154,127.1149135));
        routes.add(new LatLng(37.2749544,127.1149923));
        routes.add(new LatLng(37.2751024,127.1153186));
        routes.add(new LatLng(37.2751497,127.1154549));
        routes.add(new LatLng(37.2752452,127.1157172));
        routes.add(new LatLng(37.2752400,127.1157680));
        routes.add(new LatLng(37.2752347,127.1158109));
        routes.add(new LatLng(37.2752204,127.1158685));
        routes.add(new LatLng(37.2751765,127.1159431));
        routes.add(new LatLng(37.2750467,127.1159449));
        routes.add(new LatLng(37.2746754,127.1159491));
        routeInfo.get("명지대").put("기흥역", routes);

        // 기흥역 ~ 명지대
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2746754,127.1159491));
        routes.add(new LatLng(37.2738939,127.1159564));
        routes.add(new LatLng(37.2738533,127.1159566));
        routes.add(new LatLng(37.2737965,127.1159569));
        routes.add(new LatLng(37.2736938,127.1159541));
        routes.add(new LatLng(37.2733719,127.1159467));
        routes.add(new LatLng(37.2728599,127.1159369));
        routes.add(new LatLng(37.2727716,127.1159283));
        routes.add(new LatLng(37.2727301,127.1159217));
        routes.add(new LatLng(37.2726705,127.1159085));
        routes.add(new LatLng(37.2725404,127.1158009));
        routes.add(new LatLng(37.2723723,127.1156562));
        routes.add(new LatLng(37.2722294,127.1155115));
        routes.add(new LatLng(37.2721271,127.1153958));
        routes.add(new LatLng(37.2720691,127.1153070));
        routes.add(new LatLng(37.2720102,127.1151967));
        routes.add(new LatLng(37.2719875,127.1151314));
        routes.add(new LatLng(37.2719057,127.1149412));
        routes.add(new LatLng(37.2718767,127.1148929));
        routes.add(new LatLng(37.2718387,127.1148547));
        routes.add(new LatLng(37.2717881,127.1148234));
        routes.add(new LatLng(37.2716663,127.1147699));
        routes.add(new LatLng(37.2716329,127.1147554));
        routes.add(new LatLng(37.2715670,127.1147445));
        routes.add(new LatLng(37.2715165,127.1147357));
        routes.add(new LatLng(37.2714309,127.1147248));
        routes.add(new LatLng(37.2713804,127.1147251));
        routes.add(new LatLng(37.2713385,127.1148652));
        routes.add(new LatLng(37.2713136,127.1149837));
        routes.add(new LatLng(37.2713076,127.1150616));
        routes.add(new LatLng(37.2713059,127.1151146));
        routes.add(new LatLng(37.2713138,127.1155860));
        routes.add(new LatLng(37.2713089,127.1157564));
        routes.add(new LatLng(37.2712694,127.1163532));
        routes.add(new LatLng(37.2711824,127.1170564));
        routes.add(new LatLng(37.2710716,127.1176299));
        routes.add(new LatLng(37.2709683,127.1180162));
        routes.add(new LatLng(37.2707353,127.1187031));
        routes.add(new LatLng(37.2700291,127.1205553));
        routes.add(new LatLng(37.2698574,127.1212487));
        routes.add(new LatLng(37.2695505,127.1219754));
        routes.add(new LatLng(37.2694145,127.1222964));
        routes.add(new LatLng(37.2691872,127.1228153));
        routes.add(new LatLng(37.2691067,127.1229995));
        routes.add(new LatLng(37.2686619,127.1240180));
        routes.add(new LatLng(37.2682868,127.1248341));
        routes.add(new LatLng(37.2682170,127.1249992));
        routes.add(new LatLng(37.2680310,127.1254456));
        routes.add(new LatLng(37.2678368,127.1259112));
        routes.add(new LatLng(37.2678073,127.1259824));
        routes.add(new LatLng(37.2676838,127.1262774));
        routes.add(new LatLng(37.2672203,127.1273455));
        routes.add(new LatLng(37.2670673,127.1277207));
        routes.add(new LatLng(37.2667568,127.1284362));
        routes.add(new LatLng(37.2665501,127.1289300));
        routes.add(new LatLng(37.2660741,127.1300478));
        routes.add(new LatLng(37.2660338,127.1301495));
        routes.add(new LatLng(37.2658720,127.1305620));
        routes.add(new LatLng(37.2655389,127.1312809));
        routes.add(new LatLng(37.2653391,127.1316789));
        routes.add(new LatLng(37.2651678,127.1319707));
        routes.add(new LatLng(37.2650074,127.1322523));
        routes.add(new LatLng(37.2648710,127.1324492));
        routes.add(new LatLng(37.2648019,127.1325488));
        routes.add(new LatLng(37.2644543,127.1329587));
        routes.add(new LatLng(37.2641840,127.1332837));
        routes.add(new LatLng(37.2638407,127.1336271));
        routes.add(new LatLng(37.2636186,127.1338097));
        routes.add(new LatLng(37.2633659,127.1340038));
        routes.add(new LatLng(37.2627893,127.1343777));
        routes.add(new LatLng(37.2624725,127.1345416));
        routes.add(new LatLng(37.2623131,127.1345931));
        routes.add(new LatLng(37.2621285,127.1346538));
        routes.add(new LatLng(37.2618746,127.1347430));
        routes.add(new LatLng(37.2616711,127.1348026));
        routes.add(new LatLng(37.2614495,127.1348522));
        routes.add(new LatLng(37.2612639,127.1348925));
        routes.add(new LatLng(37.2606135,127.1350118));
        routes.add(new LatLng(37.2604179,127.1350331));
        routes.add(new LatLng(37.2598530,127.1351125));
        routes.add(new LatLng(37.2596665,127.1351348));
        routes.add(new LatLng(37.2581906,127.1353190));
        routes.add(new LatLng(37.2573257,127.1354697));
        routes.add(new LatLng(37.2570222,127.1355479));
        routes.add(new LatLng(37.2568519,127.1355938));
        routes.add(new LatLng(37.2566845,127.1356544));
        routes.add(new LatLng(37.2563676,127.1357867));
        routes.add(new LatLng(37.2561462,127.1359028));
        routes.add(new LatLng(37.2559474,127.1360380));
        routes.add(new LatLng(37.2556667,127.1362231));
        routes.add(new LatLng(37.2554769,127.1363537));
        routes.add(new LatLng(37.2552980,127.1365113));
        routes.add(new LatLng(37.2551245,127.1366655));
        routes.add(new LatLng(37.2547678,127.1370304));
        routes.add(new LatLng(37.2543672,127.1374833));
        routes.add(new LatLng(37.2543510,127.1375015));
        routes.add(new LatLng(37.2543330,127.1375218));
        routes.add(new LatLng(37.2536244,127.1383597));
        routes.add(new LatLng(37.2533460,127.1386892));
        routes.add(new LatLng(37.2532858,127.1387605));
        routes.add(new LatLng(37.2529538,127.1392639));
        routes.add(new LatLng(37.2521567,127.1406592));
        routes.add(new LatLng(37.2515607,127.1417851));
        routes.add(new LatLng(37.2515239,127.1418507));
        routes.add(new LatLng(37.2513509,127.1421526));
        routes.add(new LatLng(37.2509268,127.1429191));
        routes.add(new LatLng(37.2499991,127.1446566));
        routes.add(new LatLng(37.2499892,127.1446747));
        routes.add(new LatLng(37.2499588,127.1447312));
        routes.add(new LatLng(37.2498682,127.1449030));
        routes.add(new LatLng(37.2487190,127.1470283));
        routes.add(new LatLng(37.2485405,127.1473268));
        routes.add(new LatLng(37.2482953,127.1476301));
        routes.add(new LatLng(37.2478368,127.1483392));
        routes.add(new LatLng(37.2473083,127.1491355));
        routes.add(new LatLng(37.2469772,127.1496726));
        routes.add(new LatLng(37.2469449,127.1497280));
        routes.add(new LatLng(37.2467494,127.1500593));
        routes.add(new LatLng(37.2461452,127.1511896));
        routes.add(new LatLng(37.2452532,127.1528636));
        routes.add(new LatLng(37.2440903,127.1549909));
        routes.add(new LatLng(37.2438670,127.1553696));
        routes.add(new LatLng(37.2432922,127.1563915));
        routes.add(new LatLng(37.2430043,127.1569092));
        routes.add(new LatLng(37.2426823,127.1574496));
        routes.add(new LatLng(37.2424888,127.1578778));
        routes.add(new LatLng(37.2423263,127.1581040));
        routes.add(new LatLng(37.2421593,127.1583348));
        routes.add(new LatLng(37.2417104,127.1589659));
        routes.add(new LatLng(37.2415084,127.1592397));
        routes.add(new LatLng(37.2413602,127.1594410));
        routes.add(new LatLng(37.2411367,127.1597476));
        routes.add(new LatLng(37.2407011,127.1603144));
        routes.add(new LatLng(37.2405709,127.1604931));
        routes.add(new LatLng(37.2403967,127.1607205));
        routes.add(new LatLng(37.2403078,127.1608438));
        routes.add(new LatLng(37.2401911,127.1609965));
        routes.add(new LatLng(37.2399091,127.1613811));
        routes.add(new LatLng(37.2395859,127.1618211));
        routes.add(new LatLng(37.2389413,127.1627327));
        routes.add(new LatLng(37.2385084,127.1632927));
        routes.add(new LatLng(37.2382480,127.1636502));
        routes.add(new LatLng(37.2378224,127.1642259));
        routes.add(new LatLng(37.2377137,127.1643729));
        routes.add(new LatLng(37.2374291,127.1647541));
        routes.add(new LatLng(37.2368754,127.1656292));
        routes.add(new LatLng(37.2366302,127.1659493));
        routes.add(new LatLng(37.2362297,127.1664742));
        routes.add(new LatLng(37.2362117,127.1665036));
        routes.add(new LatLng(37.2358536,127.1670700));
        routes.add(new LatLng(37.2356887,127.1674033));
        routes.add(new LatLng(37.2352583,127.1682056));
        routes.add(new LatLng(37.2351839,127.1683435));
        routes.add(new LatLng(37.2348119,127.1691129));
        routes.add(new LatLng(37.2346883,127.1693682));
        routes.add(new LatLng(37.2338306,127.1715004));
        routes.add(new LatLng(37.2334401,127.1727004));
        routes.add(new LatLng(37.2333867,127.1729622));
        routes.add(new LatLng(37.2331575,127.1741988));
        routes.add(new LatLng(37.2331247,127.1743748));
        routes.add(new LatLng(37.2331041,127.1744493));
        routes.add(new LatLng(37.2330809,127.1745328));
        routes.add(new LatLng(37.2328864,127.1752337));
        routes.add(new LatLng(37.2325165,127.1764630));
        routes.add(new LatLng(37.2323421,127.1769417));
        routes.add(new LatLng(37.2321962,127.1773425));
        routes.add(new LatLng(37.2319319,127.1779389));
        routes.add(new LatLng(37.2318414,127.1781399));
        routes.add(new LatLng(37.2314129,127.1790087));
        routes.add(new LatLng(37.2310076,127.1797837));
        routes.add(new LatLng(37.2308560,127.1800730));
        routes.add(new LatLng(37.2306418,127.1804966));
        routes.add(new LatLng(37.2304176,127.1809248));
        routes.add(new LatLng(37.2299369,127.1818456));
        routes.add(new LatLng(37.2297701,127.1821664));
        routes.add(new LatLng(37.2295898,127.1825133));
        routes.add(new LatLng(37.2293756,127.1829730));
        routes.add(new LatLng(37.2291767,127.1833999));
        routes.add(new LatLng(37.2289240,127.1836535));
        routes.add(new LatLng(37.2284515,127.1849248));
        routes.add(new LatLng(37.2282629,127.1855433));
        routes.add(new LatLng(37.2280764,127.1862576));
        routes.add(new LatLng(37.2278496,127.1870747));
        routes.add(new LatLng(37.2277925,127.1872959));
        routes.add(new LatLng(37.2276955,127.1874282));
        routes.add(new LatLng(37.2276560,127.1874689));
        routes.add(new LatLng(37.2276182,127.1875085));
        routes.add(new LatLng(37.2275085,127.1875913));
        routes.add(new LatLng(37.2271750,127.1875994));
        routes.add(new LatLng(37.2266912,127.1877220));
        routes.add(new LatLng(37.2266831,127.1877243));
        routes.add(new LatLng(37.2263498,127.1877990));
        routes.add(new LatLng(37.2262246,127.1878367));
        routes.add(new LatLng(37.2258247,127.1879623));
        routes.add(new LatLng(37.2257184,127.1879876));
        routes.add(new LatLng(37.2257040,127.1879899));
        routes.add(new LatLng(37.2255995,127.1879959));
        routes.add(new LatLng(37.2255003,127.1879986));
        routes.add(new LatLng(37.2253704,127.1879642));
        routes.add(new LatLng(37.2252666,127.1879161));
        routes.add(new LatLng(37.2252513,127.1879083));
        routes.add(new LatLng(37.2251556,127.1878625));
        routes.add(new LatLng(37.2251547,127.1878625));
        routes.add(new LatLng(37.2251195,127.1878457));
        routes.add(new LatLng(37.2250401,127.1878100));
        routes.add(new LatLng(37.2250428,127.1877953));
        routes.add(new LatLng(37.2250418,127.1877773));
        routes.add(new LatLng(37.2250355,127.1877570));
        routes.add(new LatLng(37.2250273,127.1877424));
        routes.add(new LatLng(37.2250156,127.1877312));
        routes.add(new LatLng(37.2250020,127.1877245));
        routes.add(new LatLng(37.2249867,127.1877234));
        routes.add(new LatLng(37.2249741,127.1877246));
        routes.add(new LatLng(37.2249678,127.1877280));
        routes.add(new LatLng(37.2249624,127.1877303));
        routes.add(new LatLng(37.2249498,127.1877461));
        routes.add(new LatLng(37.2249399,127.1877743));
        routes.add(new LatLng(37.2249382,127.1877901));
        routes.add(new LatLng(37.2248940,127.1877903));
        routes.add(new LatLng(37.2248237,127.1877917));
        routes.add(new LatLng(37.2247813,127.1877919));
        routes.add(new LatLng(37.2246984,127.1878013));
        routes.add(new LatLng(37.2246930,127.1878024));
        routes.add(new LatLng(37.2246633,127.1878082));
        routeInfo.get("기흥역").put("명지대", routes);

        // 제1공학관 ~ 명현관
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2226740,127.1868132));
        routes.add(new LatLng(37.2226278,127.1867232));
        routes.add(new LatLng(37.2225081,127.1864622));
        routes.add(new LatLng(37.2225326,127.1861994));
        routes.add(new LatLng(37.2225397,127.1858015));
        routes.add(new LatLng(37.2225509,127.1856234));
        routes.add(new LatLng(37.2225580,127.1855636));
        routes.add(new LatLng(37.2225732,127.1855117));
        routes.add(new LatLng(37.2226073,127.1854473));
        routes.add(new LatLng(37.2226944,127.1853297));
        routes.add(new LatLng(37.2230583,127.1849066));
        routes.add(new LatLng(37.2230646,127.1848931));
        routes.add(new LatLng(37.2230834,127.1848502));
        routes.add(new LatLng(37.2231049,127.1847802));
        routes.add(new LatLng(37.2231155,127.1846922));
        routes.add(new LatLng(37.2231171,127.1846291));
        routes.add(new LatLng(37.2231070,127.1845581));
        routes.add(new LatLng(37.2230160,127.1842372));
        routes.add(new LatLng(37.2229829,127.1840119));
        routes.add(new LatLng(37.2229826,127.1838699));
        routes.add(new LatLng(37.2229831,127.1837369));
        routes.add(new LatLng(37.2230677,127.1833623));
        routes.add(new LatLng(37.2230704,127.1833488));
        routes.add(new LatLng(37.2231060,127.1831672));
        routes.add(new LatLng(37.2231076,127.1830871));
        routes.add(new LatLng(37.2230719,127.1829137));
        routes.add(new LatLng(37.2230665,127.1828866));
        routes.add(new LatLng(37.2229999,127.1826119));
        routes.add(new LatLng(37.2230000,127.1823211));
        routes.add(new LatLng(37.2230086,127.1821599));
        routes.add(new LatLng(37.2230165,127.1817506));
        routes.add(new LatLng(37.2230362,127.1816897));
        routes.add(new LatLng(37.2230928,127.1815982));
        routes.add(new LatLng(37.2231629,127.1815314));
        routes.add(new LatLng(37.2232673,127.1814655));
        routes.add(new LatLng(37.2234041,127.1813985));
        routeInfo.get("제1공학관").put("명현관", routes);

        // 중앙공영주차장 ~ 명지대역
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2339179,127.2089891));
        routes.add(new LatLng(37.2334961,127.2090245));
        routes.add(new LatLng(37.2334051,127.2090350));
        routes.add(new LatLng(37.2333826,127.2090385));
        routes.add(new LatLng(37.2333240,127.2090489));
        routes.add(new LatLng(37.2332528,127.2090582));
        routes.add(new LatLng(37.2332425,127.2088970));
        routes.add(new LatLng(37.2332369,127.2088136));
        routes.add(new LatLng(37.2332106,127.2083774));
        routes.add(new LatLng(37.2331815,127.2079052));
        routes.add(new LatLng(37.2331749,127.2077992));
        routes.add(new LatLng(37.2330968,127.2064625));
        routes.add(new LatLng(37.2330816,127.2061424));
        routes.add(new LatLng(37.2330122,127.2046940));
        routes.add(new LatLng(37.2329034,127.2044780));
        routes.add(new LatLng(37.2328910,127.2041872));
        routes.add(new LatLng(37.2328898,127.2040745));
        routes.add(new LatLng(37.2328776,127.2035232));
        routes.add(new LatLng(37.2328790,127.2033609));
        routes.add(new LatLng(37.2328788,127.2032741));
        routes.add(new LatLng(37.2328800,127.2030430));
        routes.add(new LatLng(37.2328832,127.2024996));
        routes.add(new LatLng(37.2328829,127.2023993));
        routes.add(new LatLng(37.2328844,127.2022865));
        routes.add(new LatLng(37.2328875,127.2013373));
        routes.add(new LatLng(37.2328912,127.2010239));
        routes.add(new LatLng(37.2328867,127.2006801));
        routes.add(new LatLng(37.2328855,127.2005437));
        routes.add(new LatLng(37.2328831,127.2003374));
        routes.add(new LatLng(37.2328856,127.2002393));
        routes.add(new LatLng(37.2328881,127.2001637));
        routes.add(new LatLng(37.2328969,127.2000758));
        routes.add(new LatLng(37.2329040,127.2000205));
        routes.add(new LatLng(37.2329243,127.1998750));
        routes.add(new LatLng(37.2329660,127.1995997));
        routes.add(new LatLng(37.2332329,127.1989121));
        routes.add(new LatLng(37.2332741,127.1988116));
        routes.add(new LatLng(37.2334039,127.1984842));
        routes.add(new LatLng(37.2334075,127.1984740));
        routes.add(new LatLng(37.2335446,127.1981590));
        routes.add(new LatLng(37.2338995,127.1973368));
        routes.add(new LatLng(37.2340384,127.1970319));
        routes.add(new LatLng(37.2343324,127.1963870));
        routes.add(new LatLng(37.2344490,127.1961486));
        routes.add(new LatLng(37.2345100,127.1960289));
        routes.add(new LatLng(37.2345620,127.1959272));
        routes.add(new LatLng(37.2346051,127.1958425));
        routes.add(new LatLng(37.2346266,127.1957996));
        routes.add(new LatLng(37.2347493,127.1954868));
        routes.add(new LatLng(37.2348165,127.1953242));
        routes.add(new LatLng(37.2348577,127.1952248));
        routes.add(new LatLng(37.2349408,127.1949494));
        routes.add(new LatLng(37.2350095,127.1946831));
        routes.add(new LatLng(37.2350380,127.1945601));
        routes.add(new LatLng(37.2350586,127.1944743));
        routes.add(new LatLng(37.2351570,127.1942022));
        routes.add(new LatLng(37.2352338,127.1939178));
        routes.add(new LatLng(37.2352614,127.1937801));
        routes.add(new LatLng(37.2352881,127.1936459));
        routes.add(new LatLng(37.2354678,127.1927511));
        routes.add(new LatLng(37.2355275,127.1924533));
        routes.add(new LatLng(37.2355746,127.1922175));
        routes.add(new LatLng(37.2356040,127.1920764));
        routes.add(new LatLng(37.2356663,127.1917706));
        routes.add(new LatLng(37.2357455,127.1913746));
        routes.add(new LatLng(37.2358684,127.1907856));
        routes.add(new LatLng(37.2361204,127.1896211));
        routes.add(new LatLng(37.2361542,127.1894519));
        routes.add(new LatLng(37.2361871,127.1892894));
        routes.add(new LatLng(37.2362014,127.1892228));
        routes.add(new LatLng(37.2362402,127.1892351));
        routes.add(new LatLng(37.2362664,127.1892440));
        routes.add(new LatLng(37.2368915,127.1894387));
        routes.add(new LatLng(37.2370368,127.1894843));
        routes.add(new LatLng(37.2371243,127.1895121));
        routes.add(new LatLng(37.2371847,127.1895311));
        routes.add(new LatLng(37.2373065,127.1895700));
        routes.add(new LatLng(37.2377368,127.1897058));
        routes.add(new LatLng(37.2378677,127.1897470));
        routes.add(new LatLng(37.2379498,127.1897748));
        routes.add(new LatLng(37.2382339,127.1898526));
        routes.add(new LatLng(37.2382465,127.1898548));
        routes.add(new LatLng(37.2383169,127.1898748));
        routes.add(new LatLng(37.2383554,127.1897720));
        routes.add(new LatLng(37.2383679,127.1897359));
        routes.add(new LatLng(37.2384125,127.1895632));
        routeInfo.get("중앙공영주차장").put("명지대역", routes);
    }

    /**
     * 싱글톤 패턴
     * @return 싱글톤 instance
     */
    public static StationInfo getInstance() {
        if(instance == null) {
            instance = new StationInfo();
        }
        return instance;
    }

    /**
     * 특정 정류장에 대한 위치 정보
     * @param station 정류장명
     * @return 정류장 위치 정보
     */
    public LatLng getLatLng(String station) {
        return stationInfo.get(station);
    }

    /**
     * 명지대역 노선 정류장 정보
     * @return 명지대역 노선 정류장
     */
    public String[] getStationList_MjuStation() {
        return new String[]{"명지대", "이마트·상공회의소", "진입로", "명지대역", "진입로(명지대방향)", "이마트·상공회의소(명지대방향)", "명진당", "제3공학관"};
    }

    /**
     * 시내 노선 정류장 정보
     * @return 시내 노선 정류장 
     */
    public String[] getStationList_DownTown() {
        return new String[]{"명지대", "이마트·상공회의소", "진입로", "진입로(명지대방향)", "이마트·상공회의소(명지대방향)", "동부경찰서", "용인시장", "중앙공영주차장", "제1공학관", "제3공학관"};
    }

    /**
     * 기흥역 정류장 정보
     * @return 기흥역 노선 정류장
     */
    public String[] getStationList_Giheung() {
        return new String[]{"명지대", "기흥역", "명지대"};
    }

    /**
     * 방학 중 셔틀버스 정류장 정보
     * @return 방학 중 셔틀버스 정류장 정보
     */
    public String[] getStationList_Vacation() {
        return new String[]{"명현관", "함박관", "명지대", "이마트·상공회의소", "진입로", "동부경찰서", "용인시장", "중앙공영주차장", "명지대역", "진입로(명지대방향)", "이마트·상공회의소(명지대방향)", "제1공학관", "명현관"};
    }

    /**
     * 모든 정류장
     * @return 모든 정류장
     */
    public String[] getStationList_ALL() {
        return new String[] {"이마트·상공회의소", "진입로", "동부경찰서", "용인시장", "중앙공영주차장", "명지대역", "진입로(명지대방향)", "이마트·상공회의소(명지대방향)", "기흥역", "명지대"};
    }
    /**
     * 명지대역 경로
     * @return 경로들
     */
    public ArrayList<LatLng> getPolyList_MjuStation() {
        String[] stations = new String[]{"명지대", "이마트·상공회의소", "진입로", "명지대역", "진입로(명지대방향)", "이마트·상공회의소(명지대방향)", "명진당", "제3공학관"};
        ArrayList<LatLng> routes = new ArrayList<>();
        for (int i = 0; i < stations.length - 1; i++) {
            routes.addAll(routeInfo.get(stations[i]).get(stations[i+1]));
        }
        return routes;
    }

    /**
     * 시내 경로
     * @return 경로들
     */
    public ArrayList<LatLng> getPolyList_DownTown() {
        String[] stations = new String[]{"명지대", "이마트·상공회의소", "진입로", "동부경찰서", "용인시장", "중앙공영주차장", "진입로(명지대방향)", "이마트·상공회의소(명지대방향)", "제1공학관", "제3공학관"};
        ArrayList<LatLng> routes = new ArrayList<>();
        for (int i = 0; i < stations.length - 1; i++) {
            routes.addAll(routeInfo.get(stations[i]).get(stations[i+1]));
        }
        return routes;
    }

    /**
     * 기흥역 경로
     * @return 경로들
     */
    public ArrayList<LatLng> getPolyList_Giheung() {
        String[] stations = new String[]{"명지대", "기흥역", "명지대"};
        ArrayList<LatLng> routes = new ArrayList<>();
        for (int i = 0; i < stations.length - 1; i++) {
            routes.addAll(routeInfo.get(stations[i]).get(stations[i+1]));
        }
        return routes;
    }

    /**
     * 방학 중 셔틀버스 경로
     * @return 경로들
     */
    public ArrayList<LatLng> getPolyList_Vacation() {
        String[] stations = new String[]{"명현관", "함박관", "명지대 정문", "이마트·상공회의소", "진입로", "동부경찰서", "용인시장", "중앙공영주차장", "명지대역", "진입로(명지대방향)", "이마트·상공회의소(명지대방향)", "제1공학관", "명현관"};
        ArrayList<LatLng> routes = new ArrayList<>();
        for (int i = 0; i < stations.length - 1; i++) {
            routes.addAll(routeInfo.get(stations[i]).get(stations[i+1]));
        }
        return routes;
    }

    public List<LatLng> getPolyList_restStation(ArrayList<String> restStations) {
        ArrayList<LatLng> routes = new ArrayList<>();
        for(int i=0; i < restStations.size() - 1; i++){
            routes.addAll(routeInfo.get(restStations.get(i)).get(restStations.get(i+1)));
        }
        return routes;
    }
}
