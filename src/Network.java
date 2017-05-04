import java.util.*;

/**
 * Created by Vinshit on 5/3/2017.
 */
public class Network {

    Double learning_rate = .7;


    private Integer[] layer_sizes;
    private Double[][] biases;
    private Double[][][] weights;
    private Random seed = new Random();

    Network(Integer[] sizes, Double learning_rate){
        this(sizes);
        this.learning_rate = learning_rate;
    }

    Network(Integer[] sizes){
        layer_sizes = sizes;
        //create biases array
        biases = new Double[layer_sizes.length-1][];
        weights = new Double[layer_sizes.length-1][][];
        for(int i = 1; i < layer_sizes.length; i++){
            biases[i-1] = new Double[layer_sizes[i]];
            weights[i-1] = new Double[layer_sizes[i]][];
            for(int i2 = 0; i2<layer_sizes[i]; i2++){
                biases[i-1][i2] = seed.nextDouble();
                weights[i-1][i2] = new Double[layer_sizes[i-1]];
                for(int i3 = 0; i3<layer_sizes[i-1]; i3++){
                    weights[i-1][i2][i3] = seed.nextDouble();
                }
            }
        }
    }

    private Double[] feedfoward(Double[] activation){
        Double[] result_activation = activation;
        for(int i = 0; i<weights.length;i++){
            Double[] next_activation = new Double[weights[i].length];
            for(int i2 = 0;i2<weights[i].length;i2++){
                next_activation[i2] = 0.0;
                for(int i3 =0;i3<weights[i][i2].length;i3++){
                    next_activation[i2] += weights[i][i2][i3]*result_activation[i3];
                }
                next_activation[i2] += biases[i][i2];
            }
            result_activation = next_activation;
        }
        return result_activation;
    }

    private void stochasticGradientDescent(Double[][][][] training_data,int stages,int sample_batch_size,Double[][] result_test){

        for(int i = 0;i<stages;i++){
            List<Double[][][]>training_data_list = Arrays.asList(training_data);
            Collections.shuffle(training_data_list);
            List<Double[][][]> sample_batches = new ArrayList<>();
            for(int j = 0;j<sample_batch_size;j++){
//                sample_batches.add(training_data_list.get(seed.nextInt()%training_data_list.size()));  Double Random?
                sample_batches.add(training_data_list.get(j*sample_batch_size%training_data_list.size()));
            }
            for(Double[][][] sample:sample_batches){
                update(sample);
            }
            if(result_test != null){
                System.out.println("Stage "+ i +" " + evaluate(result_test) +" / "+ result_test.length);
            }
            else{
                System.out.println("Stage "+ i +" completed");
            }
        }
    }

    private int evaluate(Double[][] test_result){
        int result =0;
        return result;
    }

    private void update(Double[][][] sample){ //sample[0] for x, [1] for y
        Double[][][] weight_update = new Double[weights.length][][];
        Double[][] biases_update = new Double[biases.length][];
        //BackProp
        for (int i= 0; i < sample.length;i++){
            backprop(weight_update,biases_update,sample[i]);
        }
        for (int i=0; i < weights.length;i++){
            for(int j=0; j <weights[i].length;j++){
                biases[i][j] -= (learning_rate*sample.length)*biases_update[i][j];
                for(int k =0; k<weights[i][j].length;k++){
                    weights[i][j][k] -= (learning_rate*sample.length)*weight_update[i][j][k];
                }
            }
        }
//        Double[] activation = sample[0];
//        Double[][] activation_layers = new Double[layer_sizes.length][];
//        activation_layers[0] = activation;
//        Double[][] z_layers = new Double[layer_sizes.length-1][];
//        for(int i =0; i<biases.length;i++){
//
//        }
    }

    public void backprop(Double[][][] delta_weights,Double[][] delta_biases,Double[][] sample){
        Double[] activation = sample[0];
        Double[][] activation_layers = new Double[layer_sizes.length][];
        activation_layers[0] = activation;
        Double[][] z_layers = new Double[layer_sizes.length-1][];
        for(int i =0; i<biases.length;i++){
            Double[] z = new Double[biases[i].length];
            z_layers[i] = z;
        }
    }

    static public void main(String[] args){
        Integer[] sizes = {2,3,1};
        Network network = new Network(sizes);
        Double[] activation = {0.0,1.0};
        Double[] result = network.feedfoward(activation);
        network.weights.toString();
    }
}
