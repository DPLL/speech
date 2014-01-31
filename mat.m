% read files from ./wav and add noise to each file, storing
% them in the folder of ./noise

% define the SNR
SNR = 100;

wavFiles = dir('./wav/*.wav');

for i = 1:length(wavFiles)
    inName = wavFiles(i).name;
    outName = strcat('./noise/', num2str(i), '-noise.wav');
    x = wavread(strcat('./wav/', inName));
    y = awgn(x, SNR, 'measured');
    wavwrite(y, 16000, 16, outName);
end



